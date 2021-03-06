package com.cxh.base.dao;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

/**
 * <p>自定义Base Repository 实现类
 * @author Jason
 */
public class BaseDaoImpl<T> extends SimpleJpaRepository<T, Integer> implements BaseDao<T>
{
	
	private static final Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);

	
	private final EntityManager entityManager;

	public BaseDaoImpl(JpaEntityInformation<T, Integer> entityInformation, EntityManager entityManager)
	{
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
	}

	@Override
	public T findByIdEx(Integer id)
	{
		logger.debug("=============findByIdEx");
		
		return entityManager.find(getDomainClass(), id);
	}

}
