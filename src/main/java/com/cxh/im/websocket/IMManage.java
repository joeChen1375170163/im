package com.cxh.im.websocket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.cxh.im.mq.entity.Agent;
import com.cxh.im.mq.entity.Customer;

public class IMManage
{
	/**
	 * 客户连接后进入等待队列的缓存集合
	 */
	private static Map<String, Customer> waitCustomers = new ConcurrentHashMap<>();
	/**
	 * 客服断开连接后，正在与其会话的客户会进入这个等待队列的缓存集合
	 */
	private static Map<String, Customer> agentDropWait = new ConcurrentHashMap<>();
	/**
	 * 处于与客服会话的客户Customer缓存集合，不包含等待队列中的客户
	 */
	private static Map<String, Customer> customers = new ConcurrentHashMap<>();
	/**
	 * 已连接客服Agent的缓存集合
	 */
	private static Map<String, Agent> agents = new ConcurrentHashMap<>();

	public static void putWaitCustomer(String customerId, Customer customer)
	{
		waitCustomers.put(customerId, customer);
	}

	public static void removeWaitCustomer(String customerId)
	{
		waitCustomers.remove(customerId);
	}

	public static Customer getWaitCustomer(String customerId)
	{
		return waitCustomers.get(customerId);
	}

	public static Set<Entry<String, Customer>> getWaitCustomerEntrySet()
	{
		return waitCustomers.entrySet();
	}

	public static int getWaitCustomerSize()
	{
		return waitCustomers.size();
	}

	public static Map<String, Customer> getWaitCustomersMap()
	{
		return waitCustomers;
	}
	
	public static void putAgentDropWait(String customerId, Customer customer)
	{
		agentDropWait.put(customerId, customer);
	}

	public static void removeAgentDropWait(String customerId)
	{
		agentDropWait.remove(customerId);
	}

	public static Customer getAgentDropWait(String customerId)
	{
		return agentDropWait.get(customerId);
	}

	public static Set<Entry<String, Customer>> getAgentDropWaitEntrySet()
	{
		return agentDropWait.entrySet();
	}

	public static int getAgentDropWaitSize()
	{
		return agentDropWait.size();
	}
	
	public static Map<String, Customer> getAgentDropWaitMap()
	{
		return agentDropWait;
	}

	public static void putCustomer(String customerId, Customer customer)
	{
		customers.put(customerId, customer);
	}

	public static void removeCustomer(String customerId)
	{
		customers.remove(customerId);
	}

	public static Customer getCustomer(String customerId)
	{
		return customers.get(customerId);
	}

	public static void putAgent(String agentId, Agent agent)
	{
		agents.put(agentId, agent);
	}

	public static void removeAgent(String agentId)
	{
		agents.remove(agentId);
	}

	public static Agent getAgent(String agentId)
	{
		return agents.get(agentId);
	}

	public static Set<String> getAgentKeySet()
	{
		return agents.keySet();
	}

	public static Set<Entry<String, Agent>> getAgentEntrySet()
	{
		return agents.entrySet();
	}

	public static int getAgentSize()
	{
		return agents.size();
	}

	/**
	 * 对waitCustomers 、 agentDropWait有效
	 * 根据客户进入等待队列的时间来进行升序排序
	 * @param customerMap
	 * @return 排序后的list集合
	 */
	public static List<Map.Entry<String, Customer>> getCustomerSort(Map<String, Customer> customerMap)
	{
		List<Map.Entry<String, Customer>> sortList = new ArrayList<>(customerMap.entrySet());
		Collections.sort(sortList, new Comparator<Map.Entry<String, Customer>>()
		{

			@Override
			public int compare(Entry<String, Customer> o1, Entry<String, Customer> o2)
			{
				if (o1.getValue().getQueueTime().getTime() > o2.getValue().getQueueTime().getTime())
				{
					return 1;
				}
				else if (o1.getValue().getQueueTime().getTime() < o2.getValue().getQueueTime().getTime())
				{
					return -1;
				}
				else
				{
					return 0;
				}
			}
		});
		return sortList;
	}
}
