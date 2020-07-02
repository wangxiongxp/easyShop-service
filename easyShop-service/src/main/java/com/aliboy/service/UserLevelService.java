package com.aliboy.service;

import com.aliboy.model.entity.UserLevel;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2019-12-01 22:53
 */
public interface UserLevelService {

    /**
     * 通过id查找
     */
    UserLevel findUserLevelById(Integer id);

}
