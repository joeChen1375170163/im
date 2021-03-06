package com.cxh.im.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cxh.base.action.BaseAction;
import com.cxh.base.dao.QueryParameter;
import com.cxh.common.ResponseResult;
import com.cxh.im.entity.SystemConfig;
import com.cxh.im.service.ISystemConfigService;

@Controller
@RequestMapping("/systemConfig")
public class SystemConfigAction extends BaseAction<SystemConfig>
{

    @Autowired
    private ISystemConfigService service;

    @Override
    public void fullQueryParameter(QueryParameter<SystemConfig> queryParameter)
    {
        // TODO Auto-generated method stub

    }

    @ResponseBody
    @RequestMapping("/checkItemName")
    public ResponseResult checkItemName(@RequestParam(name = "itemName") String itemName)
    {
        List<SystemConfig> findByItemName = this.service.findByItemName(itemName);
        if (findByItemName != null && findByItemName.size() > 0)
        {
            return ResponseResult.success("");
        }
        else
        {
            return ResponseResult.error("");
        }
    }

}
