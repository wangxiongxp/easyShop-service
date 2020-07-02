package com.aliboy.web.security;

import com.aliboy.common.security.AbstractCredentialsMatcher;
import com.aliboy.common.security.MyAuthenticationInfo;
import com.aliboy.common.security.PrincipalManager;
import com.aliboy.model.entity.User;
import com.aliboy.service.UserService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

public class CustomHashedCredentialsMatcher extends AbstractCredentialsMatcher {
    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    @Lazy
    private PrincipalManager principalManager;

    public CustomHashedCredentialsMatcher(CacheManager cacheManager) {
        super(cacheManager);
    }

    @Override
    protected void lockUser(String userId, String userName) {
        Integer id = Integer.parseInt(userId);
        userService.lockUserByUserId(id);
        passwordRetryCache.remove(userName);
    }

    @Override
    protected String getPasswordRetryCacheName() {
        return "passwordRetryCache";
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token,
                                      AuthenticationInfo info) {
        if (!(info instanceof MyAuthenticationInfo)) {
            throw new RuntimeException("No supported AuthenticationInfo");
        }
        MyAuthenticationInfo myInfo = (MyAuthenticationInfo) info;

        if (token instanceof WechatToken || token instanceof PhoneToken) {
            processMatches(token, myInfo);
            return true;
        }
        //对比密码
        return super.doCredentialsMatch(token, info);
    }

    private void processMatches(AuthenticationToken token, MyAuthenticationInfo myInfo) {
        String userName = (String) token.getPrincipal();
        User user = userService.findUserByName(userName);
        if (user == null) {
            // 没找到帐号
            throw new UnknownAccountException("error ref customerId");
        }
        if (user.getIsLocked()) {
            throw new LockedAccountException("error ref customerId");
        }
        principalManager.setupPrincipal(myInfo.getUserType(), myInfo.getUserId(),
                myInfo.getUsername(), myInfo.getProperties());
    }
}
