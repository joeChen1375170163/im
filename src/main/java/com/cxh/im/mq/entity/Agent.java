package com.cxh.im.mq.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.socket.WebSocketSession;

public class Agent implements Serializable
{
	private static final long serialVersionUID = 8393948391218136037L;

	private String id;
	private String name;
	private List<String> customerIds = new ArrayList<>(); // 正太服务的客户的id集合
	private WebSocketSession session;

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

	public List<String> getCustomerIds()
	{
		return customerIds;
	}

	public void setCustomerIds(List<String> customerIds)
	{
		this.customerIds = customerIds;
	}

	public WebSocketSession getSession()
	{
		return session;
	}

	public void setSession(WebSocketSession session)
	{
		this.session = session;
	}

	public void addCustomerIds(String customerId)
	{
		this.customerIds.add(customerId);
	}
}
