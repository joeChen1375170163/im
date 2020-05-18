package com.cxh.im.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxh.base.service.BaseService;
import com.cxh.im.dao.OperatorDao;
import com.cxh.im.entity.Operator;

@Service
public class OperatorService extends BaseService<Operator> implements IOperatorService
{

	@Autowired
	private OperatorDao dao;
	
	@Override
	public Operator findByWorkNo(String workNo)
	{
		return dao.findByWorkNo(workNo);
	}

}
