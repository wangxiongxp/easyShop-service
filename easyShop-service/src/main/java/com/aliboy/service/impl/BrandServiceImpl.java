package com.aliboy.service.impl;

import com.aliboy.mapper.BrandMapper;
import com.aliboy.model.entity.Brand;
import com.aliboy.service.BrandService;
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
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public Brand findBrandById(Integer id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Brand> queryHomeBrandList() {
        Map<String,Object> map = Maps.newHashMap();
        map.put("isNew", true);
        PageHelper.startPage(1, 4);

        List<Brand> list = brandMapper.queryListPage(map);
        return list;
    }
}
