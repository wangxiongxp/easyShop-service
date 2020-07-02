package com.aliboy.common.exception;

import com.aliboy.common.constants.ErrorCodes;
import com.aliboy.common.dto.ResultDto;
import com.aliboy.common.i18n.I18nMessages;
import com.aliboy.common.utils.BeanUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2020-04-15 13:33
 */
public class GlobalExceptionBaseHandler {

    final static Logger logger = LoggerFactory.getLogger(GlobalExceptionBaseHandler.class);

    @Autowired
    protected I18nMessages messageSource;

    public GlobalExceptionBaseHandler() {
    }

    @ExceptionHandler({BizException.class})
    public ResultDto defaultErrorHandler(HttpServletRequest req, BizException exception) {
        exception.printStackTrace();
        return this.getExceptionDto(exception);
    }

    @ExceptionHandler(value = AuthorizationException.class)
    public ResultDto authorizationExceptionHandler(AuthorizationException exception) {
        //401会话失效或没有登录
        return getExceptionDto((Exceptions.bizException(ErrorCodes.ERROR_401_MSG)));
    }

    @ExceptionHandler(value = UnauthenticatedException.class)
    public ResultDto unauthenticatedExceptionHandler(UnauthenticatedException exception) {
        //401会话失效或没有登录
        return getExceptionDto((Exceptions.bizException(ErrorCodes.ERROR_401_MSG)));
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public ResultDto unauthorizedExceptionHandler(HttpServletRequest req, UnauthorizedException exception) {
        String queryString = StringUtils.hasLength(req.getQueryString()) ? "?" + req.getQueryString() : "";
        String errorString = req.getMethod() + " " + req.getRequestURI() + queryString;
        return this.getExceptionDto(Exceptions.bizException(ErrorCodes.ERROR_403_MSG, errorString));
    }


    @ExceptionHandler({Exception.class})
    public ResultDto defaultErrorHandler(HttpServletRequest req, Exception exception) {
        exception.printStackTrace();

        boolean needShowDetail = "dev".equals(BeanUtils.getActiveProfiles()[0]);
        String queryString = StringUtils.hasLength(req.getQueryString()) ? "?" + req.getQueryString() : "";
        String errorString = req.getMethod() + " " + req.getRequestURI() + queryString + "\r\n";
        if (needShowDetail) {
            errorString = errorString + exception.getMessage();
        }

        return this.getExceptionDto(Exceptions.bizException(ErrorCodes.ERROR_500_MSG, errorString));
    }

    private ResultDto getExceptionDto(BizException exception) {
        ResultDto dto = new ResultDto();
        dto.setErrorCode(exception.getErrorCode());
        dto.setMsg(exception.getMessage());
        if (exception.getData() != null) {
            dto.setData(exception.getData());
        }
        return dto;
    }

}
