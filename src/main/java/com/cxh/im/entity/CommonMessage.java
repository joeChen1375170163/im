package com.cxh.im.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 常用语管理：常见问题、常用回复
 * 
 * @author Administrator
 */
@Entity
@Table(name = "common_message")
public class CommonMessage implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 533206815187654811L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** 类型：1表示常见问题，2表示常见回复 */
    @Column(name = "type")
    private String type;

    /** 常用语 */
    @Column(name = "message")
    private String message;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

}
