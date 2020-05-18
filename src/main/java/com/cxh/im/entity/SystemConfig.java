package com.cxh.im.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "system_config")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class SystemConfig implements Serializable
{
    private static final long serialVersionUID = 6536505992267163041L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** 配置项名称 */
    @Column(name = "item_name")
    private String itemName;

    /** 配置项值 */
    @Column(name = "item_value")
    private String itemValue;

    /** 操作员ID */
    @Column(name = "operator_id")
    private Integer operatorId;

    /** 配置项描述 */
    @Column(name = "item_name_cn")
    private String itemNameCn;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getItemName()
    {
        return itemName;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public String getItemValue()
    {
        return itemValue;
    }

    public void setItemValue(String itemValue)
    {
        this.itemValue = itemValue;
    }

    public Integer getOperatorId()
    {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId)
    {
        this.operatorId = operatorId;
    }

    public String getItemNameCn()
    {
        return itemNameCn;
    }

    public void setItemNameCn(String itemNameCn)
    {
        this.itemNameCn = itemNameCn;
    }

}
