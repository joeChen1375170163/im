package com.cxh.common.utils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cxh.im.entity.SystemConfig;
import com.cxh.im.service.ISystemConfigService;

/**
 * 系统配置管理工具类
 * @author Administrator
 */
@Component
public class SystemConfigManager
{
	private static SystemConfigManager systemConfigManager;
	private static final Logger logger = LogManager.getLogger(SystemConfigManager.class);

	private Map<String, String> configList = new ConcurrentHashMap<>();

	@Autowired
	private ISystemConfigService systemConfigService;

	public SystemConfigManager()
	{
		super();
		systemConfigManager = this;
	}

	/**
	 * 初始化系统配置缓存
	 * @param systemConfigService
	 */
	@Autowired
	public void setSystemConfigService(ISystemConfigService systemConfigService)
	{
		this.systemConfigService = systemConfigService;
		List<SystemConfig> systemConfigList = this.systemConfigService.queryList();
//		configList = systemConfigList.stream().collect(Collectors.toMap(SystemConfig::getItemName,SystemConfig::getItemValue));
		for (SystemConfig config : systemConfigList)
		{
			configList.put(config.getItemName(), config.getItemValue());
		}
		logger.info("SystemConfigManager 初始化..."); 
	}

	/**
	 * 根据配置项名称获取系统配置的配置项值
	 * @param  itemName 配置项名称
	 * @return          配置项值
	 */
	public static String getConfigValue(String itemName)
	{
		itemName = itemName.trim();
		if (systemConfigManager.configList.containsKey(itemName))
		{
			return systemConfigManager.configList.get(itemName);
		}
		return "";
	}

	/**
	 * 可以修改缓存中系统配置的值和向缓存中添加系统配置
	 * @param  itemName  配置项名称
	 * @param  itemValue 配置项值
	 * @return
	 */
	public static String setConfigValue(String itemName, String itemValue)
	{
		itemName = itemName.trim();
		String oldValue = getConfigValue(itemName);
		systemConfigManager.configList.put(itemName, itemValue);
		return oldValue;
	}

	/**
	 * 删除系统配置缓存中的配置
	 * @param itemName 配置项名称
	 */
	public static void deleteConfigItem(String itemName)
	{
		itemName = itemName.trim();
		if (systemConfigManager.configList.containsKey(itemName))
		{
			systemConfigManager.configList.remove(itemName);
		}
	}

	/**
	 * 获取所有的系统配置
	 * @return Map 系统配置集合
	 */
	public static Map<String, String> getConfigList()
	{
		return systemConfigManager.configList;
	}

}