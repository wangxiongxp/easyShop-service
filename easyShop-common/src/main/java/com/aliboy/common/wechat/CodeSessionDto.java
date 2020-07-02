package com.aliboy.common.wechat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@Data
public class CodeSessionDto implements Serializable {
    private static final long serialVersionUID = 1914499745307268226L;

    /**
     * wx.login获取的code
     */
    private String code;

    @JsonIgnore
    private String openId;

    @JsonIgnore
    private String unionId;

    @JsonIgnore
    private String sessionKey;

    /**
     * 凭据
     */
    private String credentials;

    private int errCode;

    private String errMsg;

    public String getUnionId() {
        return StringUtils.isNotBlank(unionId) ? unionId : openId;
    }
}
