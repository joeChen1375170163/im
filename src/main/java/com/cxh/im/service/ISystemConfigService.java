package com.cxh.im.service;

import java.util.List;

import com.cxh.base.service.IBaseService;
import com.cxh.im.entity.SystemConfig;

public interface ISystemConfigService extends IBaseService<SystemConfig>
{

	/**
	 * 通过名称查询
	 * @param  itemName 配置项名称
	 * @return
	 */
	List<SystemConfig> findByItemName(String itemName);

}
