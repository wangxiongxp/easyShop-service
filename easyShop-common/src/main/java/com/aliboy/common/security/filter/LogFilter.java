package com.aliboy.common.security.filter;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Enumeration;

/**
 *
 * @author xiquee.com <br>
 * @date 2018-11-09 10:16:00
 */
public class LogFilter extends AccessControlFilter {

    final static Logger logger = LoggerFactory.getLogger(LogFilter.class);

    @Override
    protected boolean isAccessAllowed(ServletRequest request , ServletResponse response , Object mappedValue) {
        return true;
    }


    private void getHeadersInfo(ServletRequest request) {
        Enumeration headerNames = ((ShiroHttpServletRequest) request).getHeaderNames();
        while (headerNames.hasMoreElements()) {
//            String key = (String) headerNames.nextElement();
//            String value = ((ShiroHttpServletRequest) request).getHeader(key);
//            logger.info("----->name:"+name+"----->key :" +key+"----->value:"+value);
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request , ServletResponse response) throws Exception {
        return false;
    }
}
