package com.aliboy.service.impl.center;

import com.aliboy.common.dto.Pagination;
import com.aliboy.common.dto.QueryParams;
import com.aliboy.common.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import com.aliboy.common.utils.PagedGridResult;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class BaseService {

    protected <T> PagedGridResult<T> setPagedGrid(List<?> list, Integer pageIndex) {
        PageInfo<?> pageList = new PageInfo<>(list);

        Pagination pagination = new Pagination(pageIndex, pageList.getPageSize());
        pagination.setTotalPage(pageList.getPages());
        pagination.setTotalResult((int)pageList.getTotal());

        return new PagedGridResult(list, pagination);
    }

    /**
     * @param params --页面参数
     * @return 查询参数
     */
    protected final Map<String,Object> convertQueryParams(QueryParams params) {
        Map<String,Object> pd = Maps.newHashMap();

        if (StringUtils.hasLength(params.getQuery())) {
            pd.put("query", params.getQuery());
        }

        Map<String, Object> mapParam = params.getParam();
        if (mapParam != null && mapParam.size() > 0) {
            for (Map.Entry<String, Object> entry : mapParam.entrySet()) {
                pd.put(entry.getKey(), entry.getValue());
            }
        }
        if (StringUtils.hasLength(params.getSort())) {
            pd.put("sort", params.getSort());
            // 有排序时才需要加asc与desc
            if (params.isAsc()) {
                pd.put("order", "asc");
            } else {
                pd.put("order", "desc");
            }
        }
        return pd;
    }

}
