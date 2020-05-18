package com.cxh.im.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cxh.im.entity.SystemConfig;

public class SystemConfigDaoImpl implements SystemConfigDaoEx
{
    private static final Logger logger = LoggerFactory.getLogger(SystemConfigDaoImpl.class);

    // 获取当前线程的EntityManager实例
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SystemConfig findByIdEx(Integer id)
    {
        logger.debug("findByIdEx-----------");
        return entityManager.find(SystemConfig.class, id);
    }

}
