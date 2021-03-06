package com.cxh.im.service;

public interface IRabbitProductorService
{
	/**
	 * @param exchange 交换机
	 * @param queueKey 队列key
	 * @param msg      消息
	 */
	void sendMessage(String exchange, String queueKey, String msg);
}
