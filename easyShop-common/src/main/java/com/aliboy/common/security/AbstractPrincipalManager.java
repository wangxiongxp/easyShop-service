package com.aliboy.common.security;

import com.aliboy.common.exception.BizException;
import com.aliboy.common.exception.ErrorCode;
import com.aliboy.common.exception.Exceptions;
import com.aliboy.common.constants.SystemConstant;
import com.aliboy.common.constants.UserTypes;
import com.aliboy.common.utils.SerializeUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.ImmutableProxiedSession;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.serializer.DefaultDeserializer;
import org.springframework.core.serializer.support.DeserializingConverter;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * AbstractPrincipalManager <br>
 *
 * @author xiquee.com <br>
 * @date 2018-11-09 10:16:00
 */
public abstract class AbstractPrincipalManager implements PrincipalManager {

    private static final String SPLIT_CHAR = "-";
    private static final int SPLIT_LEN = 2;

    public static final String ASSOCIATED_ACCOUNT_ID = "AssociatedAccountId";
    public static final String ASSOCIATED_ACCOUNT_NAME = "AssociatedAccountName";

    protected static final String PRINCIPAL_KEY = "global.principal";
    final static Logger logger = LoggerFactory.getLogger(AbstractPrincipalManager.class);

    protected static Map<String, Object> userLocks = new HashMap<>();

    @Autowired
    DefaultSessionManager sessionManager;

    protected DefaultPrincipal getPrincipalNullable() {
        try {
            Session session = SecurityUtils.getSubject().getSession();
            return getPrincipal(session);
        } catch (UnavailableSecurityManagerException ex) {
            throw ex;
        }
    }

    private DefaultPrincipal getPrincipal() {
        DefaultPrincipal principal = getPrincipalNullable();
        if (principal == null) {
            throw Exceptions.bizException(new ErrorCode(401, "error.401.msg"));
        }
        return principal;
    }

    @Override
    public boolean hasPrincipal() {
        DefaultPrincipal principal = this.getPrincipalNullable();
        return principal != null;
    }

    public void principalUpdated(DefaultPrincipal principal) {
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(PRINCIPAL_KEY, principal);
    }

    /**
     * @param session
     * @return
     */
    public DefaultPrincipal getPrincipal(Session session) {
        if (session == null) {
            return null;
        }
        try {
            DefaultPrincipal principal = (DefaultPrincipal) session.getAttribute(PRINCIPAL_KEY);
            if (principal == null && isAnonymousSupported()
                    && !(session instanceof ImmutableProxiedSession)) {
                principal = createAnonymousPrincipal();
                session.setAttribute(PRINCIPAL_KEY, principal);
            }
            return principal;
        } catch (ClassCastException ex) {
            //springboot-devtools
            try {
                Object obj = session.getAttribute(PRINCIPAL_KEY);
                ClassLoader cl = DefaultPrincipal.class.getClassLoader();
                DeserializingConverter dz = new DeserializingConverter(new DefaultDeserializer(cl));
                return (DefaultPrincipal) dz.convert(SerializeUtil.serialize(obj));
            } catch (Exception e) {
                return null;
            }
        } catch (UnknownSessionException ex) {
            logger.error("获取principal异常,UnknownSessionException====", ex);
            ThreadContext.remove(ThreadContext.SUBJECT_KEY);//移除线程里面的subject
            sessionManager.getSessionDAO().delete(session);//移除失效的session
            session = SecurityUtils.getSubject().getSession();
            DefaultPrincipal principal = (DefaultPrincipal) session.getAttribute(PRINCIPAL_KEY);
            logger.debug("2、重新获取到的principal值为====" + principal);
            return principal;
        } catch (Exception e) {
            logger.error("获取principal异常", e);
            throw new BizException(500, e.getMessage());
        }
    }

    public DefaultPrincipal createAnonymousPrincipal() {
        return new DefaultPrincipal(0, "0", SystemConstant.ANONYMOUS, null);
    }

    /**
     * @param userType    --域Id
     * @param userId      --账户Id
     * @param accountName --账户名称
     * @param props       --其它属性
     */
    @Override
    public void setupPrincipal(int userType, String userId, String accountName, Map<String, Object> props) {
        DefaultPrincipal principal = createPrincipal(userType, userId, accountName, props);
        SecurityUtils.getSubject().getSession().setAttribute(PRINCIPAL_KEY, principal);
    }

    @Override
    public boolean isAnonymousSupported() {
        return false;
    }

    /**
     * @param userType
     * @param userId
     * @param accountName
     * @param props
     * @return
     */
    protected DefaultPrincipal createPrincipal(int userType, String userId, String accountName, Map<String, Object> props) {
        DefaultPrincipal principal = new DefaultPrincipal(userType, userId, accountName, props);
        if (props != null) {
            if (props.containsKey(ASSOCIATED_ACCOUNT_ID)) {
                principal.setProperty(DefaultPrincipal.KEY_ASSOCIATED_ID, String.valueOf((int) props.get(ASSOCIATED_ACCOUNT_ID)));
            }
            if (props.containsKey(ASSOCIATED_ACCOUNT_NAME)) {
                principal.setProperty(DefaultPrincipal.KEY_ASSOCIATED_NAME, (String) props.get(ASSOCIATED_ACCOUNT_NAME));
            }
        }
        return principal;
    }

    /**
     *
     * @return
     */
    public int getUserType() {
        return getPrincipal().getUserType();
    }

    /**
     * 获取账户Id，可能为运营账户，供应商，或者客户.
     *
     * @return 账户Id
     */
    public String getUserId() {
        return getPrincipal().getUserId();
    }

    /**
     * 获取账户Id，可能为运营账户，供应商，或者客户.
     *
     * @return 账户Id
     */
    public int getUserIdInteger() {
        String sid = getPrincipal().getUserId();
        if (sid != null && sid.length() > 0) {
            try {
                return Integer.parseInt(sid);
            } catch (NumberFormatException ex) {
                throw new RuntimeException("no supported userId", ex);
            }
        }
        return 0;
    }

    /**
     * 获取账户登录名，可能为运营账户，供应商，或者客户.
     *
     * @return 账户登录名
     */
    public String getUserName() {
        return getPrincipal().getUserName();
    }

    /**
     * 判断是否是ROOT账户.
     *
     * @return 是否是ROOT账户
     */
    public boolean isRoot() {
        DefaultPrincipal principal = this.getPrincipalNullable();
        return principal != null &&
                this.getUserType() == UserTypes.OSS && "1".equals(this.getUserId());
    }

    public void setPreferredLanguage(String language) {
        DefaultPrincipal principal = getPrincipal();
        principal.setPreferredLanguage(language);
        principalUpdated(principal);
    }

    /**
     * 获取账户设置的默认语言环境.
     *
     * @return 账户登录名
     */
    public String getLocalLanguage() {
        DefaultPrincipal principal ;
        try {
            principal = getPrincipalNullable();
        } catch (Exception e) {
            return SystemConstant.DEFAULT_LANG;
        }
        if (principal == null) {
            return SystemConstant.DEFAULT_LANG;
        }
        String language = principal.getPreferredLanguage();
        if (language == null || language.length() == 0) {
            // 未加载语言
            language = loadPreferredLanguage();
            if (language == null || language.length() == 0) {
                //未设置语言
                language = SystemConstant.DEFAULT_LANG;
            }
            setPreferredLanguage(language);
        }
        return language;
    }

    /**
     * @return
     */
    public Locale getLocal() {
        String lang = this.getLocalLanguage();
        if (lang.indexOf(SPLIT_CHAR) > 0) {
            String[] segs = lang.split(SPLIT_CHAR);
            if (segs.length == SPLIT_LEN) {
                return new Locale(segs[0], segs[1]);
            }
        }
        return new Locale(lang);
    }

    protected abstract String loadPreferredLanguage();

    /**
     * 获取登录IP地址.
     *
     * @return 登录IP地址
     */
    public String getIPAddress() {
        DefaultPrincipal principal = getPrincipal();
        return principal.getPropertyValue(DefaultPrincipal.KEY_IP_ADDRESS, "0.0.0.0");
    }

    /**
     *
     * @param ipAddress
     */
    public void setIPAddress(String ipAddress) {
        DefaultPrincipal principal = getPrincipal();
        principal.setProperty(DefaultPrincipal.KEY_IP_ADDRESS, ipAddress);
        this.principalUpdated(principal);
    }

    public Integer getAssociatedAccountId() {
        DefaultPrincipal principal = getPrincipal();
        String aName = principal.getPropertyValue(DefaultPrincipal.KEY_ASSOCIATED_ID, "0");
        try {
            return Integer.parseInt(aName);
        } catch (Exception ex) {
            return 0;
        }
    }

    /**
     * 通过key 在properties查找对象
     */
    public String getPropertyValue(String key, String defaultValue) {
        return getPrincipal().getPropertyValue(key, defaultValue);
    }
}
