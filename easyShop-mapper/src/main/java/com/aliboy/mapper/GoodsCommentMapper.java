package com.aliboy.mapper;

import com.aliboy.my.mapper.MyMapper;
import com.aliboy.model.entity.GoodsComment;

import java.util.List;
import java.util.Map;

public interface GoodsCommentMapper extends MyMapper<GoodsComment> {

    List<GoodsComment> queryListPage(Map<String, Object> map);

}