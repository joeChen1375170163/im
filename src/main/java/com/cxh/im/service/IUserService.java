package com.cxh.im.service;

import com.cxh.base.service.IBaseService;
import com.cxh.im.entity.Customer;

public interface IUserService extends IBaseService<Customer>
{

	public Customer findByPhoneNo(String phoneNo);
	
}
