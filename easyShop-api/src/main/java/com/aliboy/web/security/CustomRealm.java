package com.aliboy.web.security;

import com.aliboy.common.constants.UserTypes;
import com.aliboy.common.security.AbstractUserRealm;
import com.aliboy.common.security.MyAuthenticationInfo;
import com.aliboy.model.entity.User;
import com.aliboy.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2020-04-22 10:14
 */
public class CustomRealm extends AbstractUserRealm {

    @Autowired
    private UserService userService;

    /**
     * 验证当前登录的Subject
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        String userName = (String) authcToken.getPrincipal();

        User user = userService.findUserByName(userName);
        if (user == null) {
            // 没找到帐号
            throw new UnknownAccountException();
        }
        if (user.getIsLocked()) {
            throw new LockedAccountException();
        }

        // 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new MyAuthenticationInfo(
                UserTypes.OSS,
                // 用户ID
                String.valueOf(user.getId()),
                // 用户名
                user.getUsername(),
                // 密码
                user.getPassword(),
                // salt=username+salt,采用明文访问时，不需要此句
                ByteSource.Util.bytes(user.getCredentialSalt()),
                // realm name
                getName()
        );
        return authenticationInfo;
    }

}
