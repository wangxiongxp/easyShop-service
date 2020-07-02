package com.aliboy.mapper;

import com.aliboy.my.mapper.MyMapper;
import com.aliboy.model.entity.Goods;

import java.util.List;
import java.util.Map;

public interface GoodsMapper extends MyMapper<Goods> {

    List<Goods> queryListPage(Map<String, Object> map);

}