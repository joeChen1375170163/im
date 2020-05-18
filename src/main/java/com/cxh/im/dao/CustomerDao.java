package com.cxh.im.dao;

import com.cxh.base.dao.BaseDao;
import com.cxh.im.entity.Customer;

public interface CustomerDao extends BaseDao<Customer> 
{

	public Customer findByPhoneNo(String phoneNo);
	
}
