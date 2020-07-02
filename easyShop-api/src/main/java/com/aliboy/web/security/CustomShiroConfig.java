package com.aliboy.web.security;

import com.aliboy.common.security.config.AbstractShiroConfiguration;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class CustomShiroConfig extends AbstractShiroConfiguration {

    /**
     * Shiro的Web过滤器Factory
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {

        /**
         * 定义Shiro过滤链,Map结构,从上向下顺序执行
         * Map中key的第一个'/'代表的路径是相对于HttpServletRequest.getContextPath()的值来的
         * anon：它对应的过滤器里面是空的,什么都没做
         * authc：该过滤器下的页面必须验证后才能访问,它是Shiro内置的一个拦截器
         * org.apache.shiro.web.filter.authc.FormAuthenticationFilter
         */
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/api/v1/index/**", "anon");
        filterMap.put("/api/v1/topic/**", "anon");
        filterMap.put("/api/v1/brand/**", "anon");
        filterMap.put("/api/v1/catalog/**", "anon");
        filterMap.put("/api/v1/goods/**", "anon");
        filterMap.put("/api/v1/auth/**", "anon");
        filterMap.put("/api/v1/**", "authc,kickout");
        filterMap.put("/**", "anon");

        return createShiroFilterFactoryBean(securityManager, filterMap);
    }

    /**
     * 自定义Realm
     */
    @Bean
    @Override
    public CustomRealm createUserRealm() {
        CustomRealm customRealm = new CustomRealm();
        //告诉realm,使用credentialsMatcher加密算法类来验证密文
        customRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        customRealm.setCachingEnabled(false);
        return customRealm;
    }

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * 所以我们需要修改下doGetAuthenticationInfo中的代码;
     * ）
     * 可以扩展凭证匹配器，实现 输入密码错误次数后锁定等功能
     */
    @Override
    @Bean(name = "credentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        CustomHashedCredentialsMatcher retryLimitHashedCredentialsMatcher =
                new CustomHashedCredentialsMatcher(getCacheManager());

        // 散列算法:这里使用MD5算法;
        retryLimitHashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 散列的次数，比如散列两次，相当于 md5(md5(""));
        retryLimitHashedCredentialsMatcher.setHashIterations(1);
        // storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
        retryLimitHashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);

        return retryLimitHashedCredentialsMatcher;
    }

    @Override
    public Cookie getCookie() {
        return new SimpleCookie("OPERATION_JSESSIONID");
    }
}

