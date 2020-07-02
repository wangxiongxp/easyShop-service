package com.aliboy.web.store;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserStore implements Serializable {

    private static final long serialVersionUID = -4278948715721523955L;

    /**
     * 用户Id
     */
    private int userId;

    /**
     * 用户名，即登录名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 头像地址
     */
    private String avatar;

    /***
     * 用户级别id
     */
    private Integer userLevelId;

    /**
     * 用户级别名称
     */
    private String userLevelName;

}
