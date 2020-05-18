package com.cxh.im.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "operator")
@Entity
public class Operator implements Serializable
{
    private static final long serialVersionUID = 9203064224465736842L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "work_no")
    private String workNo;

    @Column(name = "password")
    private String password;

    @Column(name = "activation")
    private Integer activation;// 0禁用 1启用

    @Column(name = "type")
    private Integer type; // 暂时未用到

    public Integer getActivation()
    {
        return activation;
    }

    public void setActivation(Integer activation)
    {
        this.activation = activation;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getWorkNo()
    {
        return workNo;
    }

    public void setWorkNo(String workNo)
    {
        this.workNo = workNo;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

}
