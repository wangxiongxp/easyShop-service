package com.aliboy.service.impl;

import com.aliboy.mapper.UserLevelMapper;
import com.aliboy.model.entity.UserLevel;
import com.aliboy.service.UserLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2019-12-01 22:55
 */

@Service
public class UserLevelServiceImpl implements UserLevelService {

    @Autowired
    private UserLevelMapper userLevelMapper;

    @Override
    public UserLevel findUserLevelById(Integer id) {
        return userLevelMapper.selectByPrimaryKey(id);
    }

}
