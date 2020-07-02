package com.aliboy.common.dto;

import com.aliboy.common.i18n.I18nMessageHelper;
import com.aliboy.common.exception.ErrorCode;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2020-04-15 12:16
 */
public class ResultDto {
    private int errorCode = 0;
    private String msg = "";
    private Object data;

    public ResultDto() {
    }

    public ResultDto(Object data) {
        this.setData(data);
    }

    public ResultDto(ErrorCode errorCode) {
        String message = I18nMessageHelper.getI18nMessage(errorCode.getMessageKey(), (Object[])null);
        this.errorCode = errorCode.getCode();
        this.msg = message;
    }

    public ResultDto(ErrorCode errorCode, Object... args) {
        String message = I18nMessageHelper.getI18nMessage(errorCode.getMessageKey(), args);
        this.errorCode = errorCode.getCode();
        this.msg = message;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getMsg() {
        return this.msg;
    }

    public Object getData() {
        return this.data;
    }

    public void setErrorCode(final int errorCode) {
        this.errorCode = errorCode;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public void setData(final Object data) {
        this.data = data;
    }

}
