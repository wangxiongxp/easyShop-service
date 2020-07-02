package com.aliboy.service;

import com.aliboy.model.entity.User;
import com.aliboy.model.dto.UserBO;
import org.springframework.lang.Nullable;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2019-12-01 22:53
 */
public interface UserService {

    /**
     * 判断用户名是否存在
     */
    boolean queryUsernameIsExist(String username);

    /**
     * 注册用户
     */
    User createUser(UserBO userBO);

    /**
     * 锁定账户
     */
    Boolean lockUserByUserId(Integer userId);

    /**
     * 通过name查找
     */
    User findUserByName(String username);

    /**
     * 通过mobile查找
     */
    User findUserByMobile(String mobile);

    /**
     * 通过id查找
     */
    User findUserById(Integer id);

    /**
     * 通过wechatOpenId查找
     */
    User findByOpenId(String wechatOpenId);

    /**
     * 绑定第三方授权
     * @param userId   用户Id
     * @param openId    openId
     * @return 绑定结果
     */
    Boolean bindOpenId(@Nullable Integer userId, String openId);
    /**
     * 解绑第三方授权
     *
     * @param userId    用户Id
     * @param openId     目标Id
     */
    Boolean unBindOpenId(@Nullable Integer userId, String openId);
}
