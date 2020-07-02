package com.aliboy.common.i18n.impl;

import com.aliboy.common.i18n.I18nMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2020-04-15 12:26
 */
@Component
public class I18nMessagesImpl implements I18nMessages {
    private static final String MESSAGE_NOT_FOUND = "<message not found>";
    @Autowired
    private MessageSource messageSource;

    public I18nMessagesImpl() {
    }

    public String getMessage(String key, Object[] params, String defaultMessage) {
        return this.messageSource.getMessage(key, params, defaultMessage, LocaleContextHolder.getLocale());
    }

    public String getMessage(String key, Object[] params) {
        try {
            return this.messageSource.getMessage(key, params, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException var4) {
            return "<message not found>";
        }
    }

    public String getMessage(String messageKey) {
        try {
            return this.messageSource.getMessage(messageKey, (Object[])null, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException var3) {
            return "<message not found>";
        }
    }

    public String getMessage(MessageSourceResolvable resolver) {
        try {
            return this.messageSource.getMessage(resolver, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException var3) {
            return "<message not found>";
        }
    }
}
