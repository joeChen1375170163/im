package com.cxh.im.dao;

import com.cxh.base.dao.BaseDao;
import com.cxh.im.entity.Operator;

public interface OperatorDao extends BaseDao<Operator>
{

	public Operator findByWorkNo(String workNo);
	
}
