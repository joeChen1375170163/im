package com.cxh.im.websocket;

import java.util.Date;
import java.util.Iterator;
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
import com.cxh.im.mq.entity.Customer;
import com.cxh.im.service.IRabbitProductorService;

@Component
@Scope("prototype")
public class CustomerWebSocket extends TextWebSocketHandler
{

	private static final Logger logger = LoggerFactory.getLogger(CustomerWebSocket.class);

	@Autowired
	private IRabbitProductorService rabbitProductorService;

	@Value("${imconf.exchange}")
	public String exchange;
	@Value("${imconf.agentKey}")
	public String agentKey;

	/**
	 * 建立连接后
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception
	{
		Map<String, String> params = SessionUtil.getRequestParams(session);

		String customerId = params.get("id");
		String name = params.get("name");

		// 如果参数为空，断开连接
		if (StringUtil.isEmpty(customerId) || StringUtil.isEmpty(name))
		{
			session.close();
			logger.info("[onOpen] client: id或name为空");
			return;
		}
		else
		{
			// 判断是否存在连接，存在就断开连接，不存在就新建连接
			onLeave(customerId);
		}

		Customer customer = new Customer();
		customer.setId(customerId);
		customer.setQueueTime(new Date());
		customer.setName(name);
		customer.setSession(session);

		IMManage.putWaitCustomer(customerId, customer);

		logger.info("[onOpen] client:" + params);
	}

	/**
	 * 处理消息
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception
	{
		Map<String, String> params = SessionUtil.getRequestParams(session);
		rabbitProductorService.sendMessage(exchange, agentKey, message.getPayload());
		logger.info("[onMessage] client:" + params + "; message:" + message.getPayload());
	}

	/**
	 * 处理传输错误
	 */
	@Override
	public void handleTransportError(WebSocketSession session, Throwable e) throws Exception
	{
		Map<String, String> params = SessionUtil.getRequestParams(session);
		logger.info("[onError] error! client: id=" + params, e);
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
		logger.info("[onClose] client:" + requestParams);
	}

	/**
	 * 客户断开连接时，清空缓存以及与其会话的座席
	 * @param customerId 客户id
	 */
	public void onLeave(String customerId)
	{
		try
		{
		    boolean flagreturn = true;
			Customer customer = null;
			if (null != IMManage.getCustomer(customerId))
			{
			    flagreturn = false;
				customer = IMManage.getCustomer(customerId);
				IMManage.removeCustomer(customerId);
			}
			if (null != IMManage.getWaitCustomer(customerId))
			{
			    flagreturn = false;
				customer = IMManage.getWaitCustomer(customerId);
				IMManage.removeWaitCustomer(customerId);
			}
			if (null != IMManage.getAgentDropWait(customerId))
			{
			    flagreturn = false;
				customer = IMManage.getAgentDropWait(customerId);
				IMManage.removeAgentDropWait(customerId);
			}
			if(flagreturn)
			{
				return;
			}
			if (null != customer.getSession())
			{
				customer.getSession().close();
			}
			String agentId = customer.getAgentId();
			if (!StringUtil.isEmpty(agentId))
			{
				Iterator<String> iterator = IMManage.getAgent(agentId).getCustomerIds().iterator();
				while (iterator.hasNext())
				{
					if (customerId.equals(iterator.next()))
					{
						iterator.remove();
					}
				}
				// 客户断开时给客服提示
				MessageInfo info = new MessageInfo();
				info.setContentType("2");
				info.setMsgType("2");
				if (customer.getName().length() > 8)
				{
					info.setMessage("客户" + customer.getName().substring(0, 8) + "掉线");
				}
				else
				{
					info.setMessage("客户" + customer.getName() + "掉线");
				}
				info.setReceiverId(agentId);
				info.setSenderId(customerId);
				info.setSenderName(customer.getName());
				String msg = JsonUtil.toString(info);
				if (!StringUtil.isEmpty(msg))
				{
					// 客户断开时给客服提示
					rabbitProductorService.sendMessage(exchange, agentKey, msg);
				}
				else
				{
					logger.error("客户断开后给客服提示时，将消息实体转换获得数据为null");
				}
			}

		}
		catch (Exception e)
		{
			logger.error("客户断开后的处理事件出现异常", e);
		}
	}
}
