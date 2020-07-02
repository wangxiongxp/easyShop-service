package com.aliboy.web.store;

import com.aliboy.common.cache.CacheService;
import com.aliboy.common.constants.ErrorCodes;
import com.aliboy.common.exception.Exceptions;
import com.aliboy.common.security.PrincipalUtils;
import com.aliboy.model.entity.User;
import com.aliboy.model.entity.UserLevel;
import com.aliboy.service.UserLevelService;
import com.aliboy.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class UserStoreManager {

    @Autowired
    private UserService userService;

    @Autowired
    private UserLevelService userLevelService;

    public UserStore loadUserStore(int userId) {
        User user = userService.findUserById(userId);

        if (ObjectUtils.isEmpty(user)) {
            throw Exceptions.bizException(ErrorCodes.ERROR_COMMON, "获取user异常");
        }

        UserStore userStore = new UserStore();
        userStore.setUserId(user.getId());
        userStore.setUserName(user.getUsername());
        userStore.setNickName(user.getNickname());
        userStore.setGender(user.getGender());
        userStore.setEmail(user.getEmail());
        userStore.setMobile(user.getMobile());
        userStore.setAvatar(user.getAvatar());
        userStore.setUserLevelId(user.getUserLevelId());

        UserLevel userLevel = userLevelService.findUserLevelById(user.getUserLevelId());
        userStore.setUserLevelName(userLevel.getName());

        return userStore;
    }

    /**
     * 获取当前登录用户的用户数据
     */
    public UserStore getCurrentUserStore() {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            String key = getKey();
            UserStore userStore = (UserStore) CacheService.getCacheObj(key);
            if (userStore == null) {
                int userId = PrincipalUtils.getUserIdInt();
                if (userId > 0) {
                    userStore = loadUserStore(userId);
                    CacheService.setCache(key, userStore, (int) SecurityUtils.getSubject().getSession().getTimeout());
                }
            }
            return userStore;
        }
        return null;
    }

    /**
     * 刷新User store
     */
    public void refreshUserStore() {
        CacheService.delete(getKey());
    }

    private String getKey() {
        String sessionId = SecurityUtils.getSubject().getSession().getId().toString();
        return UserStore.class.getName() + "USER_STORE_" + sessionId;
    }
}
