package com.cxh.im.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cxh.base.service.BaseService;
import com.cxh.im.dao.SystemConfigDao;
import com.cxh.im.entity.SystemConfig;

@Service
public class SystemConfigService extends BaseService<SystemConfig> implements ISystemConfigService
{

	@Autowired
	private SystemConfigDao dao;

	@Override
	public List<SystemConfig> findByItemName(String itemName)
	{
		return dao.findByItemName(itemName);
	}

}
