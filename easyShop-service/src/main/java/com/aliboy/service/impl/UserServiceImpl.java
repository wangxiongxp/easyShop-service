package com.aliboy.service.impl;

import com.aliboy.common.utils.MD5Utils;
import com.aliboy.mapper.UserMapper;
import com.aliboy.model.entity.User;
import com.aliboy.model.dto.UserBO;
import com.aliboy.service.UserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2019-12-01 22:55
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Sid sid;

    private static final String USER_FACE = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {

        Example userExample = new Example(User.class);
        Example.Criteria userCriteria = userExample.createCriteria();

        userCriteria.andEqualTo("username", username);

        User result = userMapper.selectOneByExample(userExample);

        return result == null ? false : true;
    }

    @Override
    public User createUser(UserBO userBO) {
        String userId = sid.nextShort();

        User user = new User();
//        user.setId(userId);
        user.setUsername(userBO.getUsername());
        try {
            user.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 默认用户昵称同用户名
        user.setNickname(userBO.getUsername());
//        // 默认头像
//        user.setFace(USER_FACE);
//        // 默认生日
//        user.setBirthday(DateUtil.stringToDate("1900-01-01"));
//        // 默认性别为 保密
//        user.setSex(Sex.secret.type);
//
//        user.setCreatedTime(new Date());
//        user.setUpdatedTime(new Date());

        userMapper.insert(user);

        return user;
    }

    @Override
    public Boolean lockUserByUserId(Integer userId) {
        User user = new User();
        user.setId(userId);
        user.setIsLocked(true);
        userMapper.updateByPrimaryKeySelective(user);

        return Boolean.TRUE;
    }

    @Override
    public User findUserByName(String username) {
        Example userExample = new Example(User.class);
        Example.Criteria userCriteria = userExample.createCriteria();

        userCriteria.andEqualTo("username", username);

        User result = userMapper.selectOneByExample(userExample);
        return result;
    }

    @Override
    public User findUserByMobile(String mobile) {
        Example userExample = new Example(User.class);
        Example.Criteria userCriteria = userExample.createCriteria();

        userCriteria.andEqualTo("mobile", mobile);

        return userMapper.selectOneByExample(userExample);
    }

    @Override
    public User findUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User findByOpenId(String wechatOpenId) {
        Example userExample = new Example(User.class);
        Example.Criteria userCriteria = userExample.createCriteria();

        userCriteria.andEqualTo("wechatOpenId", wechatOpenId);

        return userMapper.selectOneByExample(userExample);
    }

    @Override
    public Boolean bindOpenId(Integer userId, String openId) {

        User user = new User();
        user.setId(userId);
        user.setWechatOpenId(openId);
        userMapper.updateByPrimaryKeySelective(user);

        return Boolean.TRUE;
    }

    @Override
    public Boolean unBindOpenId(Integer userId, String openId) {
        User user = new User();
        user.setId(userId);
        user.setWechatOpenId(openId);
        userMapper.updateByPrimaryKeySelective(user);

        return Boolean.TRUE;
    }
}
