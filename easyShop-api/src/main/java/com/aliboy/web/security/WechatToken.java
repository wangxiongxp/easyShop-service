package com.aliboy.web.security;

import org.apache.shiro.authc.UsernamePasswordToken;

public class WechatToken extends UsernamePasswordToken {

    private static final long serialVersionUID = 8894266834280649003L;

    private String unionId;

    public WechatToken(String code, String unionId) {
        super(code, "");
        this.unionId = unionId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
}
