package com.aliboy.common.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AbstractPrincipal <br>
 *
 * @author xiquee.com <br>
 * @date 2018-11-09 10:16:00
 */
public class DefaultPrincipal implements Serializable {

    public static final String KEY_USER_TYPE = "__USER__TYPE__";
    public static final String KEY_USER_ID = "__USER__ID__";
    public static final String KEY_USER_NAME = "__USER__NAME__";
    public static final String KEY_IP_ADDRESS = "__USER__IP_ADDR__";
    public static final String KEY_PREFER_LANG = "__USER__PREFER__LANG__";
    public static final String KEY_ASSOCIATED_NAME = "__USER__ASSOCIATED__NAME__";
    public static final String KEY_ASSOCIATED_ID = "__USER__ASSOCIATED__ID__";

    protected  Map<String, String> roles = null;
    protected  List<String> permissions = null;
    protected  Map<String, String> properties = new HashMap<>();

    /**
     * @param userType    用户类型
     * @param userId   账户Id，可能为运营账户，或者供应商，或者客户
     * @param accountName 账户登录名，可能运营账户，或者供应商，或者客户
     */
    public DefaultPrincipal(int userType, String userId, String accountName, Map<String, Object> props) {
        this.setUserType(userType);
        this.setUserId(userId);
        this.setUserName(accountName);

        if (props != null) {
            props.forEach((k, v) -> this.setProperty(k, v == null ? "" : v.toString()));
        }
    }

    public int getUserType() {
        String stype = this.properties.get(KEY_USER_TYPE);
        try {
            return Integer.parseInt(stype);
        } catch (Exception ex){
            return 0;
        }
    }

    private void setUserType(int userType) {
        this.properties.put(KEY_USER_TYPE,String.valueOf(userType));
    }

    public String getUserId() {
        return this.properties.get(KEY_USER_ID);
    }

    private void setUserId(String userId) {
        this.properties.put(KEY_USER_ID,userId);
    }

    public String getUserName() {
        return this.properties.get(KEY_USER_NAME);
    }

    public void setUserName(String name) {
        this.properties.put(KEY_USER_NAME,name);
    }

    public void setPreferredLanguage(String language) {
        this.properties.put(KEY_PREFER_LANG,language);
    }

    public String getPreferredLanguage() {
        return this.properties.get(KEY_PREFER_LANG);
    }

    public List<String> getRoleIds() {
        if (this.roles != null) {
            return new ArrayList<>(roles.keySet());
        }
        return null;
    }

    public String getPropertyValue(String key, String defaultValue) {
        return properties.getOrDefault(key, defaultValue);
    }

    public void setProperty(String key, String value) {
        if (properties != null) {
            properties.put(key, value);
        }
    }

    /**
     * 获取角色code列表.
     *
     * @return 角色code列表
     */
    public List<String> getRoles() {
        if (this.roles != null) {
            return new ArrayList<>(roles.values());
        }
        return null;
    }

    public List<String> getPermissions() {
        if (this.permissions != null) {
            return new ArrayList<>(permissions);
        }
        return null;
    }

    public void updateRoles(Map<String,String> roles) {
        this.roles = roles;
    }

    public void updatePermissions(List<String> permissions) {
        this.permissions = permissions;
    }

}
