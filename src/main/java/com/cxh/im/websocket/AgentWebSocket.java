package com.cxh.im.websocket;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.cxh.common.utils.JsonUtil;
import com.cxh.common.utils.SessionUtil;
import com.cxh.common.utils.StringUtil;
import com.cxh.im.entity.MessageInfo;
import com.cxh.im.mq.entity.Agent;
import com.cxh.im.mq.entity.Customer;
import com.cxh.im.service.IRabbitProductorService;

@Component
@Scope("prototype")
public class AgentWebSocket extends TextWebSocketHandler
{

	private static final Logger logger = LoggerFactory.getLogger(AgentWebSocket.class);

	@Autowired
	private IRabbitProductorService rabbitProductorService;

	@Value("${imconf.exchange}")
	public String exchange;
	@Value("${imconf.customerKey}")
	public String customerKey;
	@Value("${imconf.agentKey}")
	public String agentKey;

	/**
	 * 建立连接后
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception
	{
		Map<String, String> params = SessionUtil.getRequestParams(session);
		String agentId = params.get("id");
		String agentName = params.get("name");
		// 如果参数为空，断开连接
		if (StringUtil.isEmpty(agentId) || StringUtil.isEmpty(agentName))
		{
			session.close();
			logger.info("[onOpen] client: id或name为空");
			return;
		}
		Agent agent;
		// 判断是否存在连接，存在就断开连接，不存在就新建连接
		if (null != IMManage.getAgent(agentId))
		{
			agent = IMManage.getAgent(agentId);
			if (null != agent.getSession())
			{
				agent.getSession().close();
			}
		}
		else
		{
			agent = new Agent();
			agent.setId(agentId);
		}
		agent.setName(agentName);
		agent.setSession(session);
		IMManage.putAgent(agentId, agent);

		logger.info("onOpen: agent:" + params);
	}

	/**
	 * 处理消息
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception
	{
		MessageInfo bean = JsonUtil.toBean(message.getPayload(), MessageInfo.class);
		if (null != bean)
		{
			if ("1".equals(bean.getMsgType()))
			{
				// 发给客户
				rabbitProductorService.sendMessage(exchange, customerKey, message.getPayload());
			}
			else if ("3".equals(bean.getMsgType()))
			{
				// 发给客服
				rabbitProductorService.sendMessage(exchange, agentKey, message.getPayload());
			}
		}
		Map<String, String> requestParams = SessionUtil.getRequestParams(session);
		logger.info("[onMessage] agent:" + requestParams + "; message:" + message.getPayload());
	}

	/**
	 * 处理传输错误
	 */
	@Override
	public void handleTransportError(WebSocketSession session, Throwable e) throws Exception
	{
		Map<String, String> requestParams = SessionUtil.getRequestParams(session);
		logger.error("[onError] error! agent:" + requestParams, e);
	}

	/**
	 * 连接关闭后
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception
	{
		Map<String, String> requestParams = SessionUtil.getRequestParams(session);
		if (!StringUtil.isEmpty(requestParams.get("id")))
		{
			onLeave(requestParams.get("id"));
		}
		logger.info("[onClose] agent:" + requestParams);
	}

	/**
	 * 坐席断开连接时，与其对话的客户重新分配客服，没有客服进入等待队列
	 * @param agentId   客服id
	 */
	public void onLeave(String agentId)
	{

		try
		{
			Agent agent = IMManage.getAgent(agentId);
			if (null != agent)
			{
				List<String> customerIds = agent.getCustomerIds();
				MessageInfo info = new MessageInfo();
				info.setContentType("2");
				info.setMsgType("1");
				if (agent.getName().length() > 8)
				{
					info.setMessage("客服" + agent.getName().substring(0, 8) + "掉线");
				}
				else
				{
					info.setMessage("客服" + agent.getName() + "掉线");
				}

				for (int i = 0; i < customerIds.size(); i++)
				{
					String customerId = customerIds.get(i);
					Customer customer = IMManage.getCustomer(customerId);
					if (null != customer)
					{
						customer.setAgentId("");
						customer.setQueueTime(new Date());
						IMManage.putAgentDropWait(customerId, customer);
						IMManage.removeCustomer(customerId);

						info.setReceiverId(customerId);
						info.setReceiverName(customer.getName());
						String msg = JsonUtil.toString(info);
						if (!StringUtil.isEmpty(msg))
						{
							// 客服断开时给客户提示
							rabbitProductorService.sendMessage(exchange, customerKey, msg);
						}
						else
						{
							logger.error("客服断开后给客户提示，将消息实体转换获得数据为null");
						}
					}
				}
				
				IMManage.removeAgent(agentId);
			}
		}
		catch (Exception e)
		{
			logger.error("客服断开后的处理事件出现异常", e);
		}
	}
}
