package com.aliboy.mapper;

import com.aliboy.my.mapper.MyMapper;
import com.aliboy.model.entity.Topic;

import java.util.List;
import java.util.Map;

public interface TopicMapper extends MyMapper<Topic> {

    List<Topic> queryListPage(Map<String, Object> map);

}