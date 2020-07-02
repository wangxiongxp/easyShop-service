package com.aliboy.common.exception;

import com.aliboy.common.i18n.MessageCode;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2020-04-15 12:17
 */
public class ErrorCode extends MessageCode {
    public ErrorCode(int code, String messageKey) {
        super(code, messageKey);
    }
}
