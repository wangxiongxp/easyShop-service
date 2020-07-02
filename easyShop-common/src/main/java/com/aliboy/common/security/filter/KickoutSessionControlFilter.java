package com.aliboy.common.security.filter;

import com.aliboy.common.security.OnlineSessionManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  KickoutSessionControlFilter <br>
 * @author xiquee.com <br>
 * @date 2018-11-09 10:16:00
 */
@Service
public class KickoutSessionControlFilter extends AccessControlFilter {

    final static Logger logger = LoggerFactory.getLogger(KickoutSessionControlFilter.class);

    @Autowired
    private OnlineSessionManager onlineSessionManager;

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //如果被踢出了，直接退出，重定向到踢出后的地址
        if (!checkConcurrenceLogin((HttpServletRequest)request)) {
            //会话被踢出了
            try {
                Subject subject = SecurityUtils.getSubject();
                logger.info(String.format("user [%s] with sessionId [%s] is kicked out",
                        subject.getPrincipal(),
                        subject.getSession().getId()));
                subject.logout();
            } catch (Exception e) { //ignore
                logger.warn("exception occurred when kick out user", e);
            }
            saveRequest(request);
            ((HttpServletResponse) response).sendError(401, "You are kicked out");
            response.flushBuffer();
            return false;
        }
        return true;
    }

    /**
     * 检查是否存在同一个账户多点登陆(踢出先登陆者)
     */
    private boolean checkConcurrenceLogin(HttpServletRequest request) {

        /**
         * TODO：
         * 这里暂时允许同时登录
         */
        return true;

//        Principal principal = PrincipalUtils.tryToGetPrincipal();
//        if (principal == null || principal.isRoot()) { // do nothing for root
//            return true;
//        }
//
//        Serializable sessionId = SecurityUtils.getSubject().getSession().getId();
//
//        String username = principal.getUserName();
//        String termName = TerminalUtils.getTermName(request);
//        String lastSessionId = (String) onlineSessionManager.getSessionId(username,termName);
//        return !(lastSessionId != null && !lastSessionId.equals(sessionId));
    }
}
