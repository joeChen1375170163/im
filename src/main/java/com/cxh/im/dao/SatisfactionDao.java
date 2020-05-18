package com.cxh.im.dao;

import com.cxh.base.dao.BaseDao;
import com.cxh.im.entity.Satisfaction;

public interface SatisfactionDao extends BaseDao<Satisfaction>
{

    public Satisfaction findByWorkNo(String workNo);
    
}
