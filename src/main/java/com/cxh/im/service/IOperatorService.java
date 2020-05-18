package com.cxh.im.service;

import com.cxh.base.service.IBaseService;
import com.cxh.im.entity.Operator;

public interface IOperatorService extends IBaseService<Operator>
{

	public Operator findByWorkNo(String workNo);
	
}
