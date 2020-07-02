package com.aliboy.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
public class User {
    @Id
    private Integer id;

    private String username;

    private String nickname;

    private String password;

    @Column(name = "credential_salt")
    private String credentialSalt;

    private Integer gender;

    private Integer birthday;

    private String email;

    private String mobile;

    private String avatar;

    @Column(name = "is_locked")
    private Boolean isLocked;

    @Column(name = "user_level_id")
    private Integer userLevelId;

    @Column(name = "register_time")
    private Integer registerTime;

    @Column(name = "register_ip")
    private String registerIp;

    @Column(name = "last_login_time")
    private Integer lastLoginTime;

    @Column(name = "last_login_ip")
    private String lastLoginIp;

    @Column(name = "wechat_open_id")
    private String wechatOpenId;

}