package com.cxh.im.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cxh.base.action.BaseAction;
import com.cxh.base.dao.QueryParameter;
import com.cxh.base.dao.SpecificationType;
import com.cxh.common.ResponseResult;
import com.cxh.im.entity.Operator;
import com.cxh.im.service.IOperatorService;
import com.cxh.common.utils.Encryption;

@RestController
@RequestMapping(value = "/operator/entrance")
public class OperatorLoginAction extends BaseAction<Operator>
{
    private final Logger logger = LoggerFactory.getLogger(OperatorLoginAction.class);

    @Autowired
    private IOperatorService service;

    @Override
    public void fullQueryParameter(QueryParameter<Operator> queryParameter)
    {
        queryParameter.addStringParameter("account", SpecificationType.equal);
    }

    /**
     * 添加坐席
     */
    @PostMapping("/addOperator")
    public ResponseResult addSeat(Operator operator)
    {
        try
        {
            Operator o = service.findByWorkNo(operator.getWorkNo());
            if (o != null)
            {
                return ResponseResult.error("工号已被注册！");
            }
            // 加密
            operator.setPassword(Encryption.encryptBasedDes(operator.getPassword()));
            // 0禁用 1启用
            operator.setActivation(1);
            service.save(operator);
            return ResponseResult.success("添加坐席成功！");
        }
        catch (Exception e)
        {
            logger.error("regist() 添加坐席异常：", e);
            return ResponseResult.error("添加坐席失败！");
        }
    }

    /**
     * 坐席登录
     * 
     * @param response
     * @param operator
     * @return
     */
    @PostMapping("/login")
    public ResponseResult login(HttpServletRequest request, HttpServletResponse response, Operator operator)
    {
        try
        {
            // 接收坐席实体类
            Operator o = service.findByWorkNo(operator.getWorkNo());
            if (o == null)
            {
                return ResponseResult.error("工号不存在！");
            }
            else
            {
                if (!o.getPassword().equals(Encryption.encryptBasedDes(operator.getPassword())))
                {
                    return ResponseResult.error("密码错误！");
                }
                else if (o.getActivation() != 1)
                {
                    return ResponseResult.error("该账户属于禁用状态，请联系管理员！");
                }
                else
                {
                    // 跳转主页，获取token
                    return ResponseResult.success("../chat/chat.html?workNo=" + o.getWorkNo());
                }
            }
        }
        catch (Exception e)
        {
            logger.error("login() 用户登录异常：", e);
        }

        return null;
    }

}
