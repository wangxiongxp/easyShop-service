package com.aliboy.common.security.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xiquee.com. <br>
 * @date 2018-11-09 10:16:00
 */
public class XssFilter extends OncePerRequestFilter {

    private List<String> whiteList = new ArrayList<>();

    public XssFilter(List<String> whiteUrlList) {
        if (whiteUrlList != null && whiteUrlList.size() > 0) {
            this.whiteList.addAll(whiteUrlList);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(new XssHttpServletRequestWrapper(httpServletRequest, whiteList), httpServletResponse);
    }
}
