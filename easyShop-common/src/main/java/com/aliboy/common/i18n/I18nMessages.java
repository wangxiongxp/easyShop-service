package com.aliboy.common.i18n;

import org.springframework.context.MessageSourceResolvable;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2020-04-15 12:25
 */
public interface I18nMessages {
    String getMessage(String messageKey, Object[] params, String defaultString);

    String getMessage(String messageKey, Object[] params);

    String getMessage(String messageKey);

    String getMessage(MessageSourceResolvable var1);
}
