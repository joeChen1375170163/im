package com.cxh.im.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxh.base.service.BaseService;
import com.cxh.im.dao.CommonMessageDao;
import com.cxh.im.entity.CommonMessage;

@Service
public class CommonMessageService extends BaseService<CommonMessage> implements ICommonMessageService
{

	@Autowired
	CommonMessageDao dao;
}
