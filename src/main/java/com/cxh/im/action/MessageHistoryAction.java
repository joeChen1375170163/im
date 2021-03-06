package com.cxh.im.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cxh.base.action.BaseAction;
import com.cxh.base.dao.NewDataPage;
import com.cxh.base.dao.Order;
import com.cxh.base.dao.QueryParameter;
import com.cxh.base.dao.SpecificationType;
import com.cxh.im.entity.MessageHistory;

@Controller
@RequestMapping("/history")
public class MessageHistoryAction extends BaseAction<MessageHistory>
{

    @Override
    public void fullQueryParameter(QueryParameter<MessageHistory> queryParameter)
    {
        queryParameter.addStringParameter("clientId", SpecificationType.equal);
        queryParameter.order(Order.desc("createTime"));
    }

    @ResponseBody
    @PostMapping("/queryHistory")
    public NewDataPage<MessageHistory> queryList(HttpServletRequest request)
    {
        Integer pageNum = Integer.valueOf(request.getParameter("pageNum"));
        Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
        return (NewDataPage<MessageHistory>) this.queryList(pageNum, pageSize);
    }

    // @ResponseBody
    // @PostMapping("/queryHistory")
    // public Page<MessageHistory> queryList(HttpServletRequest request,
    // @PageableDefault(value = 10, sort = { "createTime" }) Pageable pageable)
    // {
    //
    // String id = request.getParameter("clientId");
    // Page<MessageHistory> msgList = dao.findPage(id,pageable);
    // return msgList;
    // }

}
