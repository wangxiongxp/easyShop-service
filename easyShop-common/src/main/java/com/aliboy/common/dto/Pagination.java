package com.aliboy.common.dto;

import java.io.Serializable;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2020-04-16 14:55
 */
public class Pagination implements Serializable {

    public static final int MAX_PAGE_SIZE = 2000;
    public static final int MIN_RESULT = 0;

    /**
     * 当前页
     */
    private int pageIndex;
    /**
     * 每页显示记录数
     */
    private int pageSize;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 总记录数
     */
    private int totalResult;

    public Pagination() {
        this.pageSize = 10;
    }

    public Pagination(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        if (pageSize > MAX_PAGE_SIZE) {
            pageSize = MAX_PAGE_SIZE;
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取总页数（根据总记录数%每页显示记录数 计算得出）.
     * @return 计算出来的总页数
     */
    public int getTotalPage() {
        if (totalResult % pageSize == 0) {
            totalPage = totalResult / pageSize;
        } else {
            totalPage = totalResult / pageSize + 1;
        }
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

}
