package com.cxh.im.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxh.base.service.BaseService;
import com.cxh.im.dao.CustomerDao;
import com.cxh.im.entity.Customer;

@Service
public class CustomerService extends BaseService<Customer> implements IUserService
{

	@Autowired
	private CustomerDao dao;
	
	@Override
	public Customer findByPhoneNo(String phoneNo)
	{
		return dao.findByPhoneNo(phoneNo);
	}

}
