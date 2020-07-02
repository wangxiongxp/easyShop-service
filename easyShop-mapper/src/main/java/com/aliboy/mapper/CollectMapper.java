package com.aliboy.mapper;

import com.aliboy.model.entity.Collect;
import com.aliboy.my.mapper.MyMapper;

import java.util.List;
import java.util.Map;

public interface CollectMapper extends MyMapper<Collect> {

    List<Collect> queryListPage(Map<String, Object> map);
}