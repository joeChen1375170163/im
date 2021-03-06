package com.cxh.im.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "satisfaction")
@Entity
public class Satisfaction implements Serializable
{

    private static final long serialVersionUID = 2531254514333480703L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "work_no")
    private String workNo;
    @Column(name = "satisfaction")
    private String satisfaction;
    @Column(name = "details")
    private String details;
    @Column(name = "customer_phone_no")
    private String customerPhoneNo;
    @Column(name = "customer_name")
    private String customerName;

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

    public String getSatisfaction()
    {
        return satisfaction;
    }

    public void setSatisfaction(String satisfaction)
    {
        this.satisfaction = satisfaction;
    }

    public String getDetails()
    {
        return details;
    }

    public void setDetails(String details)
    {
        this.details = details;
    }

    public String getCustomerPhoneNo()
    {
        return customerPhoneNo;
    }

    public void setCustomerPhoneNo(String customerPhoneNo)
    {
        this.customerPhoneNo = customerPhoneNo;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
}
