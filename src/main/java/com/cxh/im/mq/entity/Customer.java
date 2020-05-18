package com.cxh.im.mq.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.socket.WebSocketSession;

public class Customer implements Serializable
{
	private static final long serialVersionUID = -9182518532318917273L;

	private String id;
	private String name;
	private WebSocketSession session;
	private Date queueTime; // 进入等待队列的时间
	private String agentId; // 服务的客服id

	public Date getQueueTime()
	{
		return queueTime;
	}

	public void setQueueTime(Date queueTime)
	{
		this.queueTime = queueTime;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public WebSocketSession getSession()
	{
		return session;
	}

	public void setSession(WebSocketSession session)
	{
		this.session = session;
	}

	public String getAgentId()
	{
		return agentId;
	}

	public void setAgentId(String agentId)
	{
		this.agentId = agentId;
	}

}
