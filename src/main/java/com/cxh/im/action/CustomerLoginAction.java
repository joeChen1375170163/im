package com.cxh.im.action;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cxh.base.action.BaseAction;
import com.cxh.base.dao.QueryParameter;
import com.cxh.common.ResponseResult;
import com.cxh.common.utils.StringUtil;
import com.cxh.common.utils.VerifyUtil;
import com.cxh.im.entity.Customer;
import com.cxh.im.service.IUserService;
import com.cxh.im.websocket.IMManage;

@Controller
@RequestMapping("/customer/entrance")
public class CustomerLoginAction extends BaseAction<Customer>
{
    private final Logger logger = LoggerFactory.getLogger(CustomerLoginAction.class);

    @Autowired
    private IUserService service;

    @Override
    public void fullQueryParameter(QueryParameter<Customer> queryParameter)
    {

    }

    /**
     * 用户注册
     * 
     * @param request
     * @param customer
     * @param verify
     * @return
     */
    @RequestMapping("/regist")
    @ResponseBody
    public ResponseResult regist(HttpServletRequest request, Customer customer,
            @RequestParam(name = "phoneNo") String phoneNo, @RequestParam(name = "verificationCode") String code,
            @RequestParam(name = "verify") String verify)
    {
        try
        {
            // 验证手机号重复
            Customer c = service.findByPhoneNo(phoneNo);
            if (c != null)
            {
                return ResponseResult.error("手机号已被注册！");
            }

            // 验证码验证
            HttpSession session = request.getSession();
            if (StringUtil.isEmpty(verify) || verify.equals(""))
            {
                return ResponseResult.error("验证码不能为空！");
            }
            else if (!verify.toLowerCase().equals(session.getAttribute("imageCode").toString().toLowerCase()))
            {
                return ResponseResult.error("验证码错误！");
            }
            if (session.getAttribute("imageCode").toString().equals("")
                    || StringUtil.isEmpty(session.getAttribute("imageCode").toString()))
            {
                return ResponseResult.error("图片验证码不能为空！");
            }
            else if (!verify.toLowerCase().equals(session.getAttribute("imageCode").toString().toLowerCase()))
            {
                return ResponseResult.error("图片验证码错误！");
            }
            // 手机号+手机验证短信验证
            if (session.getAttribute("phoneCode" + phoneNo) == null
                    || session.getAttribute("phoneCode" + phoneNo).equals(""))
            {
                return ResponseResult.error("手机号码验证码超时，请重新发送！");
            }
            String phoneCode = session.getAttribute("phoneCode" + phoneNo).toString();
            if (!phoneCode.equals(code))
            {
                return ResponseResult.error("手机验证码错误！");
            }

            service.save(customer);
            return ResponseResult.success("用户注册成功！");
        }
        catch (Exception e)
        {
            logger.error("用户注册异常：", e);
            return ResponseResult.error("用户注册失败！");
        }
    }

    /**
     * 发送手机验证码
     * 
     * @return
     */
    @RequestMapping("/sendMail")
    @ResponseBody
    public ResponseResult sendPhoneCode(HttpServletRequest request, @RequestParam(name = "phoneNo") String phoneNo,
            @RequestParam(name = "type", required = false) String type)
    {
        try
        {
            // 验证手机号重复
            Customer c = service.findByPhoneNo(phoneNo);
            if (type.equals("regist") && c != null)
            {
                return ResponseResult.error("手机号已被注册！");
            }
            else if (type.equals("login") && c == null)
            {
                return ResponseResult.error("请先完成注册！");
            }

            HttpSession session = request.getSession();
            // 手机号格式验证
            if (Pattern.matches(StringUtil.PHONE_REG, phoneNo))
            {
                return ResponseResult.error("手机号格式错误");
            }

            // code发送给手机 并存入session
            // Integer code = (int) ((Math.random()*9+1)*100000);
            // session.setAttribute("phoneCode"+phoneNo, code);
            // 测试阶段验证码固定
            session.setAttribute("phoneCode" + phoneNo, "123456");
            session.setMaxInactiveInterval(60 * 1000);
            return ResponseResult.success("验证码发送成功！");
        }
        catch (Exception e)
        {
            logger.error("发送验证码异常：", e);
            return ResponseResult.error("发送验证码失败！");
        }
    }

    /**
     * 登录接口
     * 
     * @param request
     * @param type
     * @param phoneNo
     * @param code
     * @param verify
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult login(HttpServletRequest request, @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "phoneNo") String phoneNo, @RequestParam(name = "verificationCode") String code,
            @RequestParam(name = "verify") String verify)
    {
        // 验证号码格式
        if (Pattern.matches(StringUtil.PHONE_REG, phoneNo))
        {
            return ResponseResult.error("手机号格式错误");
        }

        // 验证是否注册
        Customer c = service.findByPhoneNo(phoneNo);
        if (c == null)
        {
            return ResponseResult.error("请先完成注册！");
        }

        HttpSession session = request.getSession();
        // 验证码验证
        if (StringUtil.isEmpty(verify) || verify.equals(""))
        {
            return ResponseResult.error("验证码不能为空！");
        }
        if (session.getAttribute("imageCode").toString().equals("")
                || StringUtil.isEmpty(session.getAttribute("imageCode").toString()))
        {
            return ResponseResult.error("图片验证码不能为空！");
        }
        else if (!verify.toLowerCase().equals(session.getAttribute("imageCode").toString().toLowerCase()))
        {
            return ResponseResult.error("图片验证码错误！");
        }
        // 手机号+手机验证短信验证
        if (session.getAttribute("phoneCode" + phoneNo) == null
                || session.getAttribute("phoneCode" + phoneNo).equals(""))
        {
            return ResponseResult.error("手机号码验证码超时，请重新发送！");
        }
        String phoneCode = session.getAttribute("phoneCode" + phoneNo).toString();
        if (!phoneCode.equals(code))
        {
            return ResponseResult.error("手机验证码错误！");
        }
        Customer u = service.findByPhoneNo(phoneNo);
        return ResponseResult.success("../chat/client.html?id=" + u.getPhoneNo() + "&name=" + u.getName());
    }

    /**
     * 生成验证码
     * 
     * @param response
     * @param request
     */
    @RequestMapping("/getcode")
    @ResponseBody
    public void getCode(HttpServletResponse response, HttpServletRequest request)
    {
        try
        {
            HttpSession session = request.getSession();
            // 利用图片工具生成图片
            // 第一个参数是生成的验证码，第二个参数是生成的图片
            Object[] objs = VerifyUtil.createImage();
            // 将验证码存入Session
            session.setAttribute("imageCode", objs[0].toString());
            // 将图片输出给浏览器
            BufferedImage image = (BufferedImage) objs[1];
            response.setContentType("image/png");
            OutputStream os = response.getOutputStream();
            ImageIO.write(image, "png", os);
        }
        catch (Exception e)
        {
            logger.error("生成图片验证码异常：", e);
        }
    }

    /**
     * 重复登陆检测
     * 
     * @param id
     * @param flag
     * @return
     */
    @PostMapping(value = "/verification")
    @ResponseBody
    public ResponseResult verification(String id, String flag)
    {
        if ("agent".equals(flag))
        {
            if (IMManage.getAgent(id) != null)
            {
                return ResponseResult.error(id + "已经登录，请勿重复登陆");
            }
        }
        else if ("customer".equals(flag))
        {
            if (IMManage.getCustomer(id) != null || IMManage.getAgentDropWait(id) != null
                    || IMManage.getWaitCustomer(id) != null)
            {
                return ResponseResult.error(id + "已经登录，请勿重复登陆");
            }
        }
        return ResponseResult.success(id + "登录成功");
    }

}
