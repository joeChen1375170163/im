package com.cxh.base.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class NewDataPage<T> implements Serializable
{
	private static final long serialVersionUID = -2298018468815538591L;

	/**
	 * 当前第n页
	 */
	private int pageNum = 1;

	/**
	 * 分页大小
	 */
	private int pageSize = 10;

	/**
	 * 总条数
	 */
	private int rowSum;

	/**
	 * 总页数
	 */
	private int pageSum;

	/**
	 * 当前页数据集合
	 */
	private List<T> dataList;

	/**
	 * 分页条件对象
	 */
	private Pageable pageable;

	/**
	 * 数据筛选条件对象
	 */
	private QueryParameter<T> queryParameter;

	/**
	 * 查询后的数据，相关分页信息
	 */
	private Page<T> page;

	public NewDataPage(Integer pageNum, Integer pageSize)
	{
		defaultPage(pageNum, pageSize, Sort.unsorted());

	}

	public NewDataPage(Integer pageNum, Integer pageSize, Sort sort)
	{
		defaultPage(pageNum, pageSize, sort);
	}

	private void defaultPage(Integer pageNum, Integer pageSize, Sort sort)
	{
		if (null != pageNum)
		{
			this.pageNum = pageNum;
		}
		if (null != pageSize)
		{
			this.pageSize = pageSize;
		}
		// PageRequest.of(pangNum, pageSize);pageNum是从0开始的
		this.pageable = PageRequest.of((this.pageNum - 1), this.pageSize, sort);
	}

	// ==============================
	public void setPage(Page<T> page)
	{
		this.page = page;
		this.rowSum = (int) page.getTotalElements();
		this.pageSum = page.getTotalPages();
		this.dataList = page.getContent();
		queryParameter.addParameter("pageSize", this.pageSize, null);
		queryParameter.addParameter("pageNum", this.pageNum, null);
		queryParameter.addParameter("hql", "", null);
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public int getPageNum()
	{
		return pageNum;
	}

	public int getRowSum()
	{
		return rowSum;
	}

	public int getPageSum()
	{
		return pageSum;
	}

	public List<T> getDataList()
	{
		return dataList;
	}

	public Pageable getPageable()
	{
		return pageable;
	}

	public QueryParameter<T> getQueryParameter()
	{
		return queryParameter;
	}

	public void setQueryParameter(QueryParameter<T> queryParameter)
	{
		this.queryParameter = (QueryParameter<T>) queryParameter;
	}

	/**
	 * public interface Page<T> extends Iterable<T> { int getNumber(); //当前第几页
	 * 返回当前页的数目。总是非负的 int getSize(); //返回当前页面的大小。 int getTotalPages(); //返回分页总数。
	 * int getNumberOfElements(); //返回当前页上的元素数。 long getTotalElements();
	 * //返回元素总数。 boolean hasPreviousPage(); //返回如果有上一页。 boolean isFirstPage();
	 * //返回当前页是否为第一页。 boolean hasNextPage(); //返回如果有下一页。 boolean isLastPage();
	 * //返回当前页是否为最后一页。 Iterator<T> iterator(); List<T> getContent();
	 * //将所有数据返回为List boolean hasContent(); //返回数据是否有内容。 Sort getSort();
	 * //返回页的排序参数。 }
	 */

	/**
	 * 分页信息抽象接口 public interface Pageable { int getPageNumber(); //返回要返回的页面. int
	 * getPageSize(); //返回要返回的项目的数量。 int getOffset(); //根据底层页面和页面大小返回偏移量。 Sort
	 * getSort(); //返回排序参数。 }
	 */

}
