package com.aliboy.common.security;

import com.aliboy.common.utils.BeanUtils;

import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * 获取当前账户下的详细信息 <br>
 *
 * @author xiquee.com <br>
 * @date 2018-11-09 10:16:00
 */
public class PrincipalUtils {
    private static PrincipalManager principalManager;
    /**
     * @return
     */
    public static PrincipalManager getPrincipalManager() {
        if (principalManager == null) {
            try {
                principalManager = BeanUtils.getBean(PrincipalManager.class);
                if (principalManager == null) {
                    //throw new RuntimeException("cannot find instance of com.common.core.service.PrincipalManager");
                    principalManager = createDefaultPrincipalManager();
                }
            }catch (Exception ex) {
                principalManager = createDefaultPrincipalManager();
            }
        }
        return principalManager;
    }

    private static PrincipalManager createDefaultPrincipalManager() {
        PrincipalManager principalManager = new PrincipalManager() {
            @Override
            public boolean hasPrincipal() {
                return false;
            }

            @Override
            public void setupPrincipal(int userType, String userId, String accountName, Map<String, Object> props) {
            }

            @Override
            public int getUserType() {
                return 0;
            }

            @Override
            public String getUserId() {
                return null;
            }

            @Override
            public int getUserIdInteger() {
                return 0;
            }

            @Override
            public String getUserName() {
                return null;
            }

            @Override
            public boolean isRoot() {
                return false;
            }

            @Override
            public List<String> getRoleIds() {
                return null;
            }

            @Override
            public String getLocalLanguage() {
                return null;
            }

            @Override
            public Locale getLocal() {
                return null;
            }

            @Override
            public void setPreferredLanguage(String language) {

            }

            @Override
            public String getIPAddress() {
                return null;
            }

            @Override
            public void setIPAddress(String ipAddress) {

            }

            @Override
            public Integer getAssociatedAccountId() {
                return null;
            }

            @Override
            public RolePermissionInfo getRolePermissionInfo() {
                return null;
            }

            @Override
            public String getPropertyValue(String key, String defaultValue) {
                return null;
            }
        };
        return principalManager;
    }

    /**
     * 获取用户类型
     * @return
     */
    public static int getUserType() {
        return getPrincipalManager().getUserType();
    }

    /**
     * 获取用户Id，可能为运营账户，或者客户.
     *
     * @return 账户Id（整形Id）
     */
    public static int getUserIdInt() {
        return getPrincipalManager().getUserIdInteger();
    }

    /**
     * 获取账户Id，可能为运营账户，或者客户.
     *
     * @return 账户Id
     */
    public static String getUserId() {
        return getPrincipalManager().getUserId();
    }

    /**
     * 获取账户登录名，可能为运营账户，供应商，或者客户.
     *
     * @return 账户登录名
     */
    public static String getUserName() {
        return getPrincipalManager().getUserName();
    }

    /**
     * 获取账户设置的默认语言环境.
     *
     * @return 账户登录名
     */
    public static String getLocalLanguage() {
        return getPrincipalManager().getLocalLanguage();
    }


    public static Integer getAssociatedAccountId() {
        return getPrincipalManager().getAssociatedAccountId();
    }

    /**
     * 获取会话存储的属性值
     * @param key           --键名
     * @param defaultValue  --缺省值
     * @return
     */
    public static String getProperty(String key, String defaultValue) {
        return getPrincipalManager().getPropertyValue(key,defaultValue);
    }
}
