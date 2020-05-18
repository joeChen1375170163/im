package com.cxh.im.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxh.base.service.BaseService;
import com.cxh.im.dao.SatisfactionDao;
import com.cxh.im.entity.Satisfaction;

@Service
public class SatisfactionService extends BaseService<Satisfaction> implements ISatisfactionService
{

    @Autowired
    private SatisfactionDao dao;

    @Override
    public Satisfaction findByWorkNo(String workNo)
    {
        return dao.findByWorkNo(workNo);
    }
    
}
