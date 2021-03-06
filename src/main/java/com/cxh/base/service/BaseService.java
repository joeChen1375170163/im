package com.cxh.base.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import com.cxh.base.dao.BaseDao;
import com.cxh.base.dao.NewDataPage;
import com.cxh.base.dao.QueryParameter;

@Transactional(rollbackFor = Exception.class)
public class BaseService<T> implements IBaseService<T>
{
    @Autowired
    private BaseDao<T> baseDao;

    @Override
    public List<T> queryForPage(NewDataPage<T> dataPage)
    {
        QueryParameter<T> queryParameter = dataPage.getQueryParameter();
        Page<T> page = null;
        if(!queryParameter.isEmpty())
        {
            page = baseDao.findAll(queryParameter.andDefault() ,dataPage.getPageable());
        }
        else
        {
            page = baseDao.findAll(dataPage.getPageable());
        }
        dataPage.setPage(page);
        return dataPage.getDataList();
    }

    @Override
    public List<T> queryList()
    {
        return baseDao.findAll();
    }

    @Override
    public T query(Serializable id)
    {
        return baseDao.getOne((Integer) id);
    }

    @Override
    public void save(T entity)
    {
        baseDao.save(entity);
    }

    @Override
    public void update(T entity)
    {
        baseDao.save(entity);
    }

    @Override
    public void delete(Serializable id)
    {
        baseDao.deleteById((Integer) id);
    }
}
