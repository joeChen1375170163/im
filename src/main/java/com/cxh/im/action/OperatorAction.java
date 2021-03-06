package com.cxh.im.action;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cxh.common.ResponseResult;
import com.cxh.im.entity.Operator;
import com.cxh.im.entity.Satisfaction;
import com.cxh.im.service.IOperatorService;
import com.cxh.im.service.ISatisfactionService;

@Controller
@RequestMapping("/operator")
public class OperatorAction
{

    private static final Logger log = LoggerFactory.getLogger(OperatorAction.class);

    @Autowired
    ISatisfactionService satisfactionService;
    @Autowired
    IOperatorService OperatorService;

    @RequestMapping("/querySatisfactionList")
    @ResponseBody
    public ResponseResult querySatisfactionList()
    {
        List<Satisfaction> satList = satisfactionService.queryList();
        return ResponseResult.other("success", satList);
    }

    /**
     * 获取所有坐席
     * @return
     */
    @RequestMapping("/getAllAgent")
    @ResponseBody
    public ResponseResult getAllAgent()
    {
        try
        {
            List<Operator> operatList = OperatorService.queryList();
            List<String> operatWorkList = operatList.stream().map(operat -> {
                return operat.getWorkNo();
            }).collect(Collectors.toList());
            return ResponseResult.success(operatWorkList);
        }
        catch (Exception e)
        {
            log.error("查询坐席列表失败：", e);
            return ResponseResult.error("查询坐席列表失败！");
        }
    }

}
