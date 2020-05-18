package com.cxh.im.service;

import com.cxh.base.service.IBaseService;
import com.cxh.im.entity.MessageHistory;
import com.cxh.im.entity.MessageInfo;

public interface IMessageHistoryService extends IBaseService<MessageHistory>
{
	/**
	 * 保存历史记录
	 * @param bean MessageInfo实体
	 */
	public void saveMsg(MessageInfo bean);
	/**
	 * 根据客户id查询上一次服务的客服id
	 * @param  customerId 客户id
	 * @return            agentId 客服id
	 */
	String queryLastAgentId(String customerId);
}
