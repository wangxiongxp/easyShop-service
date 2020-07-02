package com.aliboy.common.dto;

import lombok.Data;

@Data
public class LoginDto {

    String username;

    String password;

    /**
     * 手机号
     */
    String mobile;

    /**
     * 凭据
     */
    String credentials;

}
