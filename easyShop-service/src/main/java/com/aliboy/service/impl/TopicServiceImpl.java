package com.aliboy.service.impl;

import com.aliboy.common.dto.QueryParams;
import com.aliboy.mapper.TopicMapper;
import com.aliboy.model.entity.Topic;
import com.aliboy.service.TopicService;
import com.aliboy.service.impl.center.BaseService;
import com.aliboy.common.utils.PagedGridResult;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2019-12-03 13:29
 */

@Service
public class TopicServiceImpl extends BaseService implements TopicService {

    @Autowired
    private TopicMapper topicMapper;

    @Override
    public PagedGridResult queryTopicList(QueryParams queryParams) {

        PageHelper.startPage(queryParams.getPageIndex(), queryParams.getPageSize());

        List<Topic> list = topicMapper.queryListPage(convertQueryParams(queryParams));

        return setPagedGrid(list, queryParams.getPageIndex());
    }

    @Override
    public Topic findById(Integer id) {
        return topicMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Topic> queryRelatedList(Integer id) {
        Map<String,Object> map = Maps.newHashMap();
        PageHelper.startPage(1, 5);

        List<Topic> list = topicMapper.queryListPage(map);
        return list;
    }

    @Override
    public List<Topic> queryHomeTopicList() {
        Map<String,Object> map = Maps.newHashMap();
        PageHelper.startPage(1, 5);

        List<Topic> list = topicMapper.queryListPage(map);
        return list;
    }
}
