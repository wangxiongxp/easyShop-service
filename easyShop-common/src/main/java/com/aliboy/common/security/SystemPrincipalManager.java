package com.aliboy.common.security;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Charles. <br>
 * @date 2019-09-05 11:50:00.
 */
public abstract class SystemPrincipalManager extends AbstractPrincipalManager {
    /**
     * 获取当前账户的角色code列表及权限列表.
     * @return
     */
    @Override
    public RolePermissionInfo getRolePermissionInfo() {
        return new RolePermissionInfo(this.getRoleCodes(),this.getPermissions());
    }

    /**
     * 获取当前账户的角色Id列表.
     *
     * @return 角色Id列表
     */
    @Override
    public List<String> getRoleIds() {
        DefaultPrincipal principal = getPrincipalNullable();
        if (principal == null) {
            return new ArrayList<>();
        }
        List<String> roleIds = principal.getRoleIds();
        if (roleIds == null) {
            synchronized (getUserLock()) {
                roleIds = principal.getRoleIds();
                if (roleIds == null) {
                    Map<String,String> roles = loadRoles(this.getUserIdInteger());
                    principal.updateRoles(roles);
                    this.principalUpdated(principal);
                    roleIds = principal.getRoleIds();
                }
            }
        }
        return roleIds;
    }

    /**
     * 获取角色code列表.
     *
     * @return 角色code列表
     */
    private List<String> getRoleCodes() {
        DefaultPrincipal principal = getPrincipalNullable();
        if (principal == null) {
            return new ArrayList<>();
        }
        List<String> roleCodes = principal.getRoles();
        if (roleCodes == null) {
            synchronized (getUserLock()) {
                roleCodes = principal.getRoles();
                if (roleCodes == null) {
                    Map<String,String> roles = loadRoles(this.getUserIdInteger());
                    principal.updateRoles(roles);
                    this.principalUpdated(principal);
                    roleCodes = principal.getRoles();
                }
            }
        }
        return roleCodes;
    }

    private List<String> getPermissions() {
        DefaultPrincipal principal = getPrincipalNullable();
        if (principal == null) {
            return new ArrayList<>();
        }
        List<String> permissions = principal.getPermissions();
        if (permissions == null) {
            synchronized (getUserLock()) {
                permissions = principal.getPermissions();
                if (permissions == null) {
                    permissions = loadPermissions(this.getRoleIds());
                    principal.updatePermissions(permissions);
                    this.principalUpdated(principal);
                    permissions = principal.getPermissions();
                }
            }
        }
        return permissions;
    }

    private Map<String,String> loadRoles(int userId) {
        try {
            Map<String,String> roles = new HashMap<>();
//            MembershipService membershipService = BeanUtils.getBean(MembershipService.class);
//            List<Role> rolesList = membershipService.getRoleByMember(userId);
//            if (!CollectionUtils.isEmpty(rolesList)) {
//                rolesList.forEach(role -> {
//                    if (!ObjectUtils.isEmpty(role)) {
//                        roles.put(role.getId(), role.getCode());
//                    }
//                });
//            }
            return roles;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private List<String> loadPermissions(List<String> roleIds) {
        List<String> permissions = new ArrayList<>();

        // ROOT has all permissions by default
//        if (! this.isRoot() && ! CollectionUtils.isEmpty(roleIds)) {
//            // 从授权表（authorization）中获取权限id集合
//            AuthorizationService authorizationService = BeanUtils.getBean(AuthorizationService.class);
//            List<String> permissionList = authorizationService.getRolePermissions(roleIds);
//            permissions.addAll(permissionList);
//        }
        return permissions;
    }

    private Object getUserLock() {
        String key = this.getUserType() + this.getUserId();
        if (! userLocks.containsKey(key)) {
            userLocks.put(key,new Object());
        }
        return userLocks.get(key);
    }

}
