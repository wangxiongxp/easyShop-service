package com.aliboy.common.security;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 *  PrincipalManager <br>
 * @author xiquee.com <br>
 * @date 2018-11-09 10:16:00
 */
public interface PrincipalManager {

    boolean hasPrincipal();

    /**
     * 创建用户
     *
     * @param userType   --域Id
     * @param userId   --账户Id
     * @param accountName --账户名称
     * @param props       --其它属性
     * @return
     */
    void setupPrincipal(int userType, String userId, String accountName, Map<String, Object> props);

    /**
     * 是否支持匿名用户
     *
     * @return
     */
    default boolean isAnonymousSupported() {
        return false;
    }

    /**
     *
     * @return
     */
    int getUserType();

    /**
     * 获取账户Id，可能为运营账户，供应商，或者客户.
     *
     * @return 账户Id
     */
    String getUserId();

    /**
     * 获取账户Id，可能为运营账户，供应商，或者客户.
     *
     * @return 账户Id
     */
    int getUserIdInteger();


    /**
     * 获取账户登录名，可能为运营账户，供应商，或者客户.
     *
     * @return 账户登录名
     */
    String getUserName();

    /**
     * 判断是否是ROOT账户.
     *
     * @return 是否是ROOT账户
     */
    boolean isRoot();

    /**
     * 获取当前账户的角色Id列表.
     *
     * @return 角色Id列表
     */
    List<String> getRoleIds();

    /**
     * 获取账户设置的默认语言环境.
     *
     * @return 账户登录名
     */
    String getLocalLanguage();

    /**
     * @return
     */
    Locale getLocal();

    void setPreferredLanguage(String language);

    /**
     * 获取登录IP地址.
     *
     * @return 登录IP地址
     */
    String getIPAddress();

    /**
     *
     * @param ipAddress
     */
    void setIPAddress(String ipAddress);

    Integer getAssociatedAccountId();

    /**
     * 获取当前账户的角色code列表及权限列表.
     * @return
     */
    RolePermissionInfo getRolePermissionInfo();

    /**
     * 通过key 在properties查找对象
     * @param key
     * @param defaultValue
     * @return
     */
    String getPropertyValue(String key, String defaultValue);
}
