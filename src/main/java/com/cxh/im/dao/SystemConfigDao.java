package com.cxh.im.dao;

import java.util.List;

import com.cxh.base.dao.BaseDao;
import com.cxh.im.entity.SystemConfig;

public interface SystemConfigDao extends BaseDao<SystemConfig>, SystemConfigDaoEx
{

	/**
	 * 通过名称查询
	 * @param  itemName 配置项名称
	 * @return
	 */
	List<SystemConfig> findByItemName(String itemName);
}
