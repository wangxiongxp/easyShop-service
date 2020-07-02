package com.aliboy.common.security;

import com.aliboy.common.security.config.SystemSettings;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public abstract class AbstractCredentialsMatcher extends HashedCredentialsMatcher {

    @Autowired
    private SystemSettings systemSettings;

    @Autowired
    private PrincipalManager principalManager;

    protected Cache<String, AtomicInteger> passwordRetryCache;

    public AbstractCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache(getPasswordRetryCacheName());
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token,
                                      AuthenticationInfo info) {

        if (!(info instanceof MyAuthenticationInfo)) {
            throw new RuntimeException("No supported AuthenticationInfo");
        }
        MyAuthenticationInfo myInfo = (MyAuthenticationInfo) info;

        String userId = myInfo.getUserId();
        String accountName = myInfo.getUsername();

        //对比密码
        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            //如果正确，清除之前连续错误的计数
            passwordRetryCache.remove(accountName);
            principalManager.setupPrincipal(myInfo.getUserType(), userId,
                    myInfo.getUsername(), myInfo.getProperties());
            return true;
        }
        processRetry(accountName, userId);
        return false;
    }

    protected void processRetry(String accountName, String userId) {
        // 检查账户对应的计数器
        AtomicInteger retryCount = passwordRetryCache.get(accountName);
        if (retryCount == null) { //初始化
            retryCount = new AtomicInteger(0);

        }
        //自增计数错误计数，存放到缓存
        retryCount.incrementAndGet();
        passwordRetryCache.put(accountName, retryCount);

        //判断错误次数是否大于锁定设定值，锁定
        if (retryCount.get() >= systemSettings.getLoginAttemptTimes()) {
            lockUser(userId, accountName);
            throw new LockedAccountException();
        }
        //判断错误次数是否达到警告提示设定值，警告
        if (retryCount.get() >= (systemSettings.getLoginAttemptTimes() - systemSettings.getLeftTimesForErrorPwd())) {
            //计算剩下多少次输入机会
            int leftInputCount = systemSettings.getLoginAttemptTimes() - retryCount.get();
            SecurityUtils.getSubject().getSession().setAttribute("leftInputCount", leftInputCount);
        }
        //错误次数还没有达到警告提示设定值，提示密码错误
        if (retryCount.get() > 0 && retryCount.get()
                < (systemSettings.getLoginAttemptTimes() - systemSettings.getLeftTimesForErrorPwd())) {
            throw new AuthenticationException();
        }
    }

    protected abstract void lockUser(String accountId, String accountName);

    protected abstract String getPasswordRetryCacheName();
}
