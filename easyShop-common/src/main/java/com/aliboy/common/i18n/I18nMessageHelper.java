package com.aliboy.common.i18n;

import com.aliboy.common.utils.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2020-04-15 12:26
 */
public class I18nMessageHelper {
    private static MessageSource messageSource = null;

    public I18nMessageHelper() {
    }

    public static String getI18nMessage(String key) {
        try {
            return getMessageSource().getMessage(key, null, LocaleContextHolder.getLocale());
        } catch (Exception var3) {
            return "message key [" + key + "] not exist";
        }
    }

    public static String getI18nMessage(String key, Object[] args) {
        try {
            return getMessageSource().getMessage(key, args, LocaleContextHolder.getLocale());
        } catch (Exception var4) {
            return "message key [" + key + "] not exist";
        }
    }

    private static MessageSource getMessageSource() {
        if (messageSource == null) {
            messageSource = (MessageSource) BeanUtils.getBean(MessageSource.class);
        }

        return messageSource;
    }
}
