package com.aliboy.common.dto;

import com.aliboy.common.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2020-04-15 16:35
 */
public class QueryParams {
    private int pageIndex;
    private int pageSize;
    private String query;
    private String sort;
    private boolean asc = true;
    private Map<String, Object> param = new HashMap<>();

    public QueryParams() {
    }

    /**
     * @param pageSize  --页面大小
     * @param pageIndex --页码
     */
    public QueryParams(int pageSize, int pageIndex) {
        this(pageSize, pageIndex, null, null);
    }

    /**
     * @param pageSize  --页面大小
     * @param pageIndex --页码
     * @param q         --查询关键字
     * @param sort      --排序字段
     */
    public QueryParams(int pageSize, int pageIndex, String q, String sort) {
        this(pageSize, pageIndex, q, sort, true);
    }
    /**
     * @param pageSize  --页面大小
     * @param pageIndex --页码
     * @param q         --查询关键字
     */
    public QueryParams(int pageSize, int pageIndex, String q) {
        this(pageSize, pageIndex, q, null, true);
    }

    /**
     * @param pageSize  --页面大小
     * @param pageIndex --页码
     * @param q         --查询关键字
     * @param sort      --排序字段
     * @param asc       --升序
     */
    public QueryParams(int pageSize, int pageIndex, String q, String sort, boolean asc) {
        this(pageSize, pageIndex, q, sort, asc, "");
    }

    /**
     * @param pageSize  --页面大小
     * @param pageIndex --页码
     * @param q         --查询关键字
     * @param sort      --排序字段
     * @param asc       --升序
     * @param language  --语言
     */
    public QueryParams(int pageSize, int pageIndex, String q, String sort, boolean asc, String language) {
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.query = StringUtils.replaceSqlPattern(q);
        this.sort = sort;
        this.asc = asc;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        if (pageSize <= 0) {
            pageSize = 10;
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }

    public Map<String, Object> getParam() {
        return param;
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }

    public QueryParams put(String key, Object value) {
        this.param.put(key, value);
        return this;
    }

    public QueryParams put(Map<String, Object> map) {
        this.param = map;
        return this;
    }

    public QueryParams putIfNotNull(String key, Object value) {
        if (value != null) {
            this.param.put(key, value);
        }
        return this;
    }

    public QueryParams putIfNotBlank(String key, String value) {
        if (value != null && value.trim().length() > 0) {
            this.param.put(key, value);
        }
        return this;
    }

}
