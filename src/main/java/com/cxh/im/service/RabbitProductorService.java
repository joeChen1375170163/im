package com.cxh.im.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxh.common.utils.JsonUtil;
import com.cxh.common.utils.StringUtil;
import com.cxh.im.entity.MessageInfo;

@Service
public class RabbitProductorService implements IRabbitProductorService
{

	private final Logger logger = LoggerFactory.getLogger(RabbitProductorService.class);


	@Autowired
	AmqpTemplate amqpTemplate;

	@Override
	public void sendMessage(String exchange, String queueKey, String msg)
	{
		try
		{
			if (!StringUtil.isEmpty(msg))
			{
				amqpTemplate.convertAndSend(exchange, queueKey, msg);
				MessageInfo bean = JsonUtil.toBean(msg, MessageInfo.class);
				if (null == bean)
				{
					logger.error("RabbitProductorService sendMessage() 将消息转化成实体得到的数据为null");
					return;
				}
				logger.info("向队列：" + queueKey + " 发送消息	message:" + msg);
			}
			else
			{
				logger.error("向队列：" + queueKey + " 发送消息	message is null");
			}
		}
		catch (Exception e)
		{
			logger.error("向队列：" + queueKey + " 发送消息出现异常", e);
		}
	}

}
