package com.cxh.im.service;

import com.cxh.base.service.IBaseService;
import com.cxh.im.entity.Satisfaction;

public interface ISatisfactionService extends IBaseService<Satisfaction>
{

    public Satisfaction findByWorkNo(String workNo);
    
}
