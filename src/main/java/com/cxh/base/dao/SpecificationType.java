package com.cxh.base.dao;

/**
 * 运算符字典
 */
public enum SpecificationType
{
    /** 类似 **/
    like(" like "),

    /** 左类似 **/
    leftLike(" leftLike "),

    /** 右类似 **/
    rigthLike(" rigthLike "),

    /** 等于 **/
    equal(" == "),

    /** 不等于 **/
    notEqual(" != "),

    /** 小于 **/
    lessThan(" < "),

    /** 小于等于 **/
    lessThanAndEqual(" <= "),

    /** 大于 **/
    moreThan(" > "),

    /** 大于等于 **/
    moreThanAndEqual(" >= "), 
    
    /** 包含 **/
    in(" in "),
    
    /** 为Null */
    isNull(" is NULL "),

    /** 不为Null */
    isNotNull(" is not NULL ");

    private String name;

    private SpecificationType(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
