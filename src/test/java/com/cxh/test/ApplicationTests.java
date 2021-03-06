package com.cxh.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cxh.common.utils.JsonUtil;
import com.cxh.im.dao.SystemConfigDao;
import com.cxh.im.entity.SystemConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests
{

	private static final Logger logger = LoggerFactory.getLogger(ApplicationTests.class);

	@Autowired
	private SystemConfigDao systemConfigDao;

	@Test
	public void testBaseDaoUpdate()
	{
		SystemConfig systemConfig = systemConfigDao.findByIdEx(1);
		
		logger.info("SystemConfig:{}",JsonUtil.toString(systemConfig));
	}
}
