package com.cxh.im.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cxh.base.action.BaseAction;
import com.cxh.base.dao.QueryParameter;
import com.cxh.base.dao.SpecificationType;
import com.cxh.im.entity.CommonMessage;

@Controller
@RequestMapping("/commonMessage")
public class CommonMessageAction extends BaseAction<CommonMessage>
{

	@Override
	public void fullQueryParameter(QueryParameter<CommonMessage> queryParameter)
	{
		queryParameter.addStringParameter("type", SpecificationType.equal);
	}

}
