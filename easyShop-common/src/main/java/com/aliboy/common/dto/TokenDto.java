package com.aliboy.common.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2020-04-22 13:45
 */
@Data
public class TokenDto {
    private String token;
    private String clientId;
    private long expire;
    private Date created;

    /**
     *
     */
    public TokenDto() {
        this(UUID.randomUUID().toString().replaceAll("-", ""),
                UUID.randomUUID().toString().replaceAll("-", ""), 1800);
    }

    public TokenDto(String clientId, String token, long expire) {
        this.clientId = clientId;
        this.token = token;
        this.expire = expire;
        this.created = new Date();
    }
}
