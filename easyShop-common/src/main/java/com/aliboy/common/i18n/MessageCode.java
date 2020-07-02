package com.aliboy.common.i18n;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2020-04-15 12:18
 */
public class MessageCode {
    protected int code;
    protected String messageKey;

    public MessageCode(Integer code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessageKey() {
        return this.messageKey;
    }

    public void setCode(final int code) {
        this.code = code;
    }

    public void setMessageKey(final String messageKey) {
        this.messageKey = messageKey;
    }

}
