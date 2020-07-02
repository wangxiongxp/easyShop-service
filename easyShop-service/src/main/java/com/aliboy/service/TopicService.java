package com.aliboy.service;

import com.aliboy.common.dto.QueryParams;
import com.aliboy.model.entity.Topic;
import com.aliboy.common.utils.PagedGridResult;

import java.util.List;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2019-12-03 13:28
 */
public interface TopicService {
    /**
     * 查询专题分页列表
     * @return
     */
    PagedGridResult queryTopicList(QueryParams queryParams);

    /**
     * 通过主键id查找
     * @param id
     * @return
     */
    Topic findById(Integer id);

    /**
     * 查询推荐专题
     * @param id
     * @return
     */
    List<Topic> queryRelatedList(Integer id);

    /**
     * 查询首页专题
     * @return
     */
    List<Topic> queryHomeTopicList();

}
