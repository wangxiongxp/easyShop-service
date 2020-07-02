package com.aliboy.service;

import com.aliboy.model.entity.Ad;

import java.util.List;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2019-12-03 13:28
 */
public interface AdService {

    /**
     * 根据posId查询广告图
     * @param posId
     * @return
     */
    List<Ad> queryAdListByPosId(Integer posId);

}
