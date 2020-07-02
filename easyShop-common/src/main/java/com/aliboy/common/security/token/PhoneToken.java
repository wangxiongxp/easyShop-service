package com.aliboy.common.security.token;

import org.apache.shiro.authc.UsernamePasswordToken;

public class PhoneToken extends UsernamePasswordToken {

    private static final long serialVersionUID = 8894266834280649003L;

    private String phone;

    public PhoneToken(String code) {
        super(code, "");
    }

}
