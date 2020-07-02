package com.aliboy.common.security.filter;

import com.aliboy.common.constants.ErrorCodes;
import com.aliboy.common.dto.ResultDto;
import com.aliboy.common.utils.JsonUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 *  MyFormAuthenticationFilter <br>
 * @author xiquee.com <br>
 * @date 2018-11-09 10:16:00
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
    final static Logger logger = LoggerFactory.getLogger(MyFormAuthenticationFilter.class);

    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {

        logger.info(SecurityUtils.getSubject().getSession().getId().toString());

        ResultDto dto = new ResultDto(ErrorCodes.ERROR_401_MSG);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(JsonUtils.objectToJson(dto));
        response.flushBuffer();
    }
}
