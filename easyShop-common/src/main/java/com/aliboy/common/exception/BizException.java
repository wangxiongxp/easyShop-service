package com.aliboy.common.exception;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2020-04-15 13:30
 */
public class BizException extends RuntimeException {
    private static final long serialVersionUID = 5813738995813590123L;
    private int errorCode = -1;
    private String message;
    private Object data;

    public BizException(Object data) {
        this.data = data;
    }

    public BizException(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public BizException(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getMessage() {
        return this.message;
    }

    public Object getData() {
        return this.data;
    }

    public void setErrorCode(final int errorCode) {
        this.errorCode = errorCode;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setData(final Object data) {
        this.data = data;
    }

}
