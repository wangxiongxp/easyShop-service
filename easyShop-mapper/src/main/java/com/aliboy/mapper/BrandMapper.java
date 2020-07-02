package com.aliboy.mapper;

import com.aliboy.my.mapper.MyMapper;
import com.aliboy.model.entity.Brand;

import java.util.List;
import java.util.Map;

public interface BrandMapper extends MyMapper<Brand> {

    List<Brand> queryListPage(Map<String, Object> map);

}