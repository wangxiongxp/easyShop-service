package com.aliboy.common.security;

import com.aliboy.common.utils.StringUtils;
import com.aliboy.common.security.cache.ShiroCacheFactory;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *  在线会话管理器 <br>
 * @author xiquee.com <br>
 * @date 2018-11-09 10:16:00
 */
@Service("onlineSessionManager")
public class OnlineSessionManager {
    final static Logger logger = LoggerFactory.getLogger(OnlineSessionManager.class);

    @Autowired
    DefaultSessionManager sessionManager;

    @Autowired
    AbstractPrincipalManager principalManager;

    @Value("${logistics.session.cache.key}")
    private String onlineSessionCacheKey;

    protected Cache<String, Serializable> onlineSessionKeyCache;
    protected SessionListener sessionListener;


    @PostConstruct
    public void init() {
        onlineSessionKeyCache = ShiroCacheFactory.getInstance().getOnlineSessionKeyCache(onlineSessionCacheKey);
        sessionListener = createSessionListener();
        sessionManager.getSessionListeners().add(sessionListener);
    }

    protected SessionListener createSessionListener() {
        SessionListener sessionListener = new SessionListener() {
            @Override
            public void onStart(Session session) {
                logger.info("Session start:" + session.getId());
                if (principalManager.isAnonymousSupported()) {
                    principalManager.createAnonymousPrincipal();
                }
            }

            @Override
            public void onStop(Session session) {
                logger.info("Session stop:" + session.getId());
                DefaultPrincipal principal = getPrincipalFromSession(session);
                if (principal != null) {
                    String name = principal.getUserName();
                    removeOnlineSession(name);
                }
            }

            @Override
            public void onExpiration(Session session) {
                logger.debug("Session expired:" + session.getId());
            }
        };
        return sessionListener;
    }


    @PreDestroy
    public void destroy() {

    }

    public void addOnlineSession(String username, Serializable sessionId) {
        addOnlineSession(username,"", sessionId);
    }

    public void addOnlineSession(String username, String termName, Serializable sessionId) {
        onlineSessionKeyCache.put(genSessionKey(username,termName), sessionId);
    }

    public void removeOnlineSession(String username) {
        logger.info("移除用户："+username+"的session数据");
        onlineSessionKeyCache.remove(username);
    }

    public Serializable getSessionId(String username) {
        return getSessionId(username,"");
    }

    public Serializable getSessionId(String username,String termName) {
        return onlineSessionKeyCache.get(genSessionKey(username,termName));
    }

    private String genSessionKey(String username, String termName) {
        return StringUtils.isNotEmpty(termName) ? (username + "@@" + termName) : username;
    }

    /**
     * 获取在线用户信息
     *
     * @return
     */
    public Map<String, DefaultPrincipal> getOnlineSessions() {
        Map<String, DefaultPrincipal> map = new HashMap<>();
        Set<String> keys = onlineSessionKeyCache.keys();
        for (String key : keys) {
            Serializable sessionId = onlineSessionKeyCache.get(key);
            Session session = sessionManager.getSession(new DefaultSessionKey(sessionId));
            DefaultPrincipal principal = getPrincipalFromSession(session);
            if (principal != null) {
                map.put(key, principal);
            }
        }
        return map;
    }

    /**
     * @return
     */
    public int getOnlineCount() {
        return this.onlineSessionKeyCache.size();
    }

    /**
     * knock out session
     *
     * @param userName
     */
    public void kickoutOnlineSession(String userName) {
        Serializable sessionId = getSessionId(userName);
        if (sessionId != null && sessionId.toString().length() > 0) {
            Session session = sessionManager.getSession(new DefaultSessionKey(sessionId));
            if (session != null) {
                session.stop();
            }
        }
    }


    private DefaultPrincipal getPrincipalFromSession(Session session) {
        if (principalManager != null && principalManager instanceof AbstractPrincipalManager) {
            return (principalManager).getPrincipal(session);
        }
        return null;
    }
}
