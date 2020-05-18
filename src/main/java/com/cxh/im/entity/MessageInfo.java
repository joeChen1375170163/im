package com.cxh.im.entity;

import java.io.Serializable;

/**
 * 消息传递实体
 * 
 * @author Administrator
 */
public class MessageInfo implements Serializable
{
    private static final long serialVersionUID = 1228621413220484714L;

    private String senderId; // 发送者ID
    private String receiverId; // 接收者ID
    private String senderName; // 发送者名称
    private String receiverName; // 接收者名称
    private String message; // 消息
    private String contentType; // 消息类容类型 1：普通消息； 2：断开提示语；3:连接提示语； 4:文件； 5:图片；
    private String msgType;// 消息发送类型 1：客服发给客户；2：客户发给客服；3：客服发给客服

    public String getMsgType()
    {
        return msgType;
    }

    public void setMsgType(String msgType)
    {
        this.msgType = msgType;
    }

    public String getSenderId()
    {
        return senderId;
    }

    public void setSenderId(String senderId)
    {
        this.senderId = senderId;
    }

    public String getReceiverId()
    {
        return receiverId;
    }

    public void setReceiverId(String receiverId)
    {
        this.receiverId = receiverId;
    }

    public String getSenderName()
    {
        return senderName;
    }

    public void setSenderName(String senderName)
    {
        this.senderName = senderName;
    }

    public String getReceiverName()
    {
        return receiverName;
    }

    public void setReceiverName(String receiverName)
    {
        this.receiverName = receiverName;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getContentType()
    {
        return contentType;
    }

    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }
}