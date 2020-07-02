package com.aliboy.common.security;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Charles. <br>
 * @date 2019-09-04 11:20:00.
 */
@Data
public class RolePermissionInfo implements Serializable {
    List<String> rolesArray = new ArrayList<>();
    List<String> permissionArray = new ArrayList<>();

    public RolePermissionInfo() {
    }

    public RolePermissionInfo(List<String> roles, List<String> permissions) {
        if (roles != null) {
            rolesArray.addAll(roles);
        }
        if (permissions != null) {
            permissionArray.addAll(permissions);
        }
    }
}
