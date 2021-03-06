package com.cxh.im.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.cxh.base.dao.BaseDao;
import com.cxh.im.entity.MessageHistory;

public interface MessageHistoryDao extends BaseDao<MessageHistory>
{

	/**
	 * 根据客户id查询上一次服务的客服
	 * @param  customerId 客户id
	 * @return
	 */
	@Query(nativeQuery = true, value = "select sender_id from message_history where receiver_id = ?1 and msg_type=1 order by id desc limit 1")
	String queryLastAgentId(String customerId);

	@Query(value = " from MessageHistory m where m.msgType = 1 and ( m.receiverId = :id or m.senderId = :id ) order by createTime desc")
	public Page<MessageHistory> findPage(String id, Pageable pageable);
}
