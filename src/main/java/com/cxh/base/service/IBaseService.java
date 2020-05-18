package com.cxh.base.service;

import java.io.Serializable;
import java.util.List;

import com.cxh.base.dao.NewDataPage;

public interface IBaseService<T>
{
    /**
     * 分页查询
     */
    public List<T> queryForPage(NewDataPage<T> dataPage);

    /**
     * @param queryParameter
     * @return
     */
    public List<T> queryList();

    /**
     * 查询
     * 
     * @param id 实体的ID
     */
    public T query(Serializable id);

    /**
     * 新增实体的保存 entity 实体对象
     */
    public void save(T entity);

    /**
     * 修改的实体的保存
     * 
     * @param entity
     */
    public void update(T entity);

    /**
     * 删除实体
     * 
     * @param id
     */
    public void delete(Serializable id);
}
