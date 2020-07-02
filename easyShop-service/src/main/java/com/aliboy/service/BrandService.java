package com.aliboy.service;

import com.aliboy.model.entity.Brand;

import java.util.List;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2019-12-03 13:28
 */
public interface BrandService {

    /**
     * 根据id查找
     * @param id
     * @return
     */
    Brand findBrandById(Integer id);

    /**
     * 查询首页品牌
     * @return
     */
    List<Brand> queryHomeBrandList();

}
