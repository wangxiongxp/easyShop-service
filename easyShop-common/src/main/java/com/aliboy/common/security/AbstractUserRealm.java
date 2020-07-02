package com.aliboy.common.security;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 *
 */
public abstract class AbstractUserRealm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        //仅支持UsernamePasswordToken类型的Token
        return token instanceof UsernamePasswordToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        RolePermissionInfo permissionInfo = PrincipalUtils.getPrincipalManager().getRolePermissionInfo();
        //为当前用户设置角色和权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRoles(permissionInfo.getRolesArray());
        authorizationInfo.addStringPermissions(permissionInfo.getPermissionArray());
        return authorizationInfo;
    }

    @Override
    protected boolean isPermitted(Permission permission, AuthorizationInfo info) {
        if (! PrincipalUtils.getPrincipalManager().hasPrincipal()) {
            return false;
        }
        if (PrincipalUtils.getPrincipalManager().isRoot()) {
            return true;
        }
        return super.isPermitted(permission, info);
    }
}

