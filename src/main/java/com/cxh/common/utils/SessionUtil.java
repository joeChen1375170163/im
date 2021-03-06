package com.cxh.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;


public class SessionUtil {

	private static final Logger logger = LoggerFactory.getLogger(SessionUtil.class);
	
	/**
	 * 获取WebSocketSession中的请求参数
	 * @param session
	 * @return
	 */
	public static Map<String, String> getRequestParams(WebSocketSession session) {
		Map<String, String> map = new HashMap<>();
		try
		{
			String[] split = session.getUri().getQuery().split("&");

			for (int i = 0; i < split.length; i++)
			{
				String[] split2 = split[i].split("=");
				map.put(split2[0], split2[1]);
			}
		}
		catch (Exception e)
		{
			logger.error("SessionUtil getRequestParams() error", e);
		}
		return map;
	}
}
