package com.aliboy.common.security.filter;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  logistics. <br>
 * Date : 2018年02月05日 9:54 <br>
 *
 * @author : xiquee.com
 */
public class OauthSessionControlFilter extends AccessControlFilter {
    /**
     * Returns <code>true</code> if the request is allowed to proceed through the filter normally, or <code>false</code>
     * if the request should be handled by the
     * {@link #onAccessDenied(ServletRequest, ServletResponse, Object) onAccessDenied(request,response,mappedValue)}
     * method instead.
     *
     * @param request     the incoming <code>ServletRequest</code>
     * @param response    the outgoing <code>ServletResponse</code>
     * @param mappedValue the filter-specific config value mapped to this filter in the URL rules mappings.
     * @return <code>true</code> if the request should proceed through the filter normally, <code>false</code> if the
     * request should be processed by this filter's
     * {@link #onAccessDenied(ServletRequest, ServletResponse, Object)} method instead.
     * @throws Exception if an error occurs during processing.
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        String token = "token";
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        return httpRequest.getHeader(token) != null &&
                httpRequest.getHeader(token).equals(httpRequest.getSession().getAttribute(token));
    }

    /**
     * Processes requests where the subject was denied access as determined by the
     * {@link #isAccessAllowed(ServletRequest, ServletResponse, Object) isAccessAllowed}
     * method.
     *
     * @param request  the incoming <code>ServletRequest</code>
     * @param response the outgoing <code>ServletResponse</code>
     * @return <code>true</code> if the request should continue to be processed; false if the subclass will
     * handle/render the response directly.
     * @throws Exception if there is an error processing the request.
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String token = "token";
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        if (httpRequest.getHeader(token) == null ||
                !httpRequest.getHeader(token).equals(httpRequest.getSession().getAttribute(token))) {
            saveRequest(request);
            ((HttpServletResponse) response).sendError(403, "Permission denied");
            response.flushBuffer();
            return false;
        }
        return true;
    }
}
