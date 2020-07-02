package com.aliboy.common.utils;

import com.aliboy.common.dto.Pagination;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * 
 * @Title: PagedGridResult.java
 * @Package com.aliboy.common.utils
 * @Description: 用来返回分页Grid的数据格式
 * Copyright: Copyright (c) 2019
 */
public class PagedGridResult<T> {
	
	private int currentPage;	// 当前页数
	private int pageSize;	    // 每页条数
	private int totalPages;		// 总页数
	private long count;		    // 总记录数

	private List<T> data;		// 每行显示的内容
	private Pagination pagination;

	public PagedGridResult() {
		this.data = Lists.newArrayList();
	}

	public PagedGridResult(List<T> data) {
		this.data = data;
	}

	public PagedGridResult(List<T> data, Pagination pagination) {
		this.data = data;
		this.pagination = pagination;

		this.currentPage=pagination.getPageIndex();
		this.pageSize=pagination.getPageSize();
		this.totalPages=pagination.getTotalPage();
		this.count=pagination.getTotalResult();
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public static <T> PagedGridResult<T> of(List<T> data, Pagination pagination) {
		PagedGridResult pagedGridResult = new PagedGridResult(data);
		pagedGridResult.setPagination(pagination);

		pagedGridResult.setCurrentPage(pagination.getPageIndex());
		pagedGridResult.setPageSize(pagination.getPageSize());
		pagedGridResult.setTotalPages(pagination.getTotalPage());
		pagedGridResult.setCount(pagination.getTotalResult());
		return pagedGridResult;
	}

}
