package com.aliboy.common.exception;

import com.aliboy.common.i18n.I18nMessageHelper;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2020-04-15 13:30
 */
public class Exceptions {
    public Exceptions() {
    }

    public static BizException bizException(ErrorCode code) {
        return bizException(code, null);
    }

    public static BizException bizException(ErrorCode errorCode, Object... args) {
        String message = I18nMessageHelper.getI18nMessage(errorCode.getMessageKey(), args);
        return new BizException(errorCode.getCode(), message);
    }
}
