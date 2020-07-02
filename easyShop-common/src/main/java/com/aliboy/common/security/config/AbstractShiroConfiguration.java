package com.aliboy.common.security.config;

import com.aliboy.common.security.filter.*;
import com.aliboy.common.security.AbstractUserRealm;
import com.aliboy.common.security.XTokenWebSessionManager;
import com.aliboy.common.security.cache.ShiroCacheFactory;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.AbstractSessionManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractShiroConfiguration {

    final static Logger logger = LoggerFactory.getLogger(AbstractShiroConfiguration.class);

    protected ShiroFilterFactoryBean createShiroFilterFactoryBean(SecurityManager securityManager,
                                                                  Map<String, String> filterChainDefinitionMap) {
        logger.info("注入Shiro的Web过滤器-->shiroFilter：{}", ShiroFilterFactoryBean.class);
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.getFilters().put("authc", new MyFormAuthenticationFilter());
        shiroFilterFactoryBean.getFilters().put("roles", new MyRolesAuthorizationFilter());
        shiroFilterFactoryBean.getFilters().put("perms", new MyPermissionsAuthorizationFilter());
        shiroFilterFactoryBean.getFilters().put("kickout", createKickoutSessionControlFilter());
        //shiroFilterFactoryBean.getFilters().put("log",new LogFilter());

        //shiroFilterFactoryBean.getFilters().put("oauth2", createOauthSessionControlFilter());
        //Shiro的核心安全接口,这个属性是必须的
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    public abstract AbstractUserRealm createUserRealm();

    public abstract HashedCredentialsMatcher hashedCredentialsMatcher();

    protected String getActiveSessionsCacheName() {
        //use default name "shiro-activeSessionCache"
        return "shiro-activeSessionCache";
    }
    @Bean("myFilterRegistration")
    public FilterRegistrationBean myFilterRegistration() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ASYNC);
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        return filterRegistration;
    }

    /**
     * 不指定名字的话，自动创建一个方法名第一个字母小写的bean
     *
     * @return
     * @Bean(name = "securityManager")
     */
    @Bean
    public SecurityManager securityManager() {
        logger.info("注入Shiro的Web过滤器-->securityManager，{}", ShiroFilterFactoryBean.class);
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        List<Realm> reams = new ArrayList<>();
        reams.add(createUserRealm());
        securityManager.setRealms(reams);
        securityManager.setCacheManager(getCacheManager());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    @Bean
    public DefaultSessionManager sessionManager() {
        XTokenWebSessionManager manager = new XTokenWebSessionManager();
        EnterpriseCacheSessionDAO dao = new EnterpriseCacheSessionDAO();
        dao.setActiveSessionsCacheName(getActiveSessionsCacheName());
        manager.setSessionDAO(dao);

        long timeout = getSessionTimeout();
        if (timeout > 0) {
            manager.setGlobalSessionTimeout(timeout);
            manager.setSessionValidationInterval(timeout);
        }
        manager.setSessionIdCookieEnabled(true);
        manager.setSessionIdCookie(getCookie());
        return manager;
    }

    public abstract Cookie getCookie();

    @Bean
    public KickoutSessionControlFilter createKickoutSessionControlFilter() {
        return new KickoutSessionControlFilter();
    }

    @Bean
    public OauthSessionControlFilter createOauthSessionControlFilter() {
        return new OauthSessionControlFilter();
    }

    /**
     * Shiro生命周期处理器
     *
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     *
     * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

    protected CacheManager getCacheManager() {
        return ShiroCacheFactory.getInstance().getCacheManager();
    }

    protected long getSessionTimeout() {
        return AbstractSessionManager.DEFAULT_GLOBAL_SESSION_TIMEOUT * 16;
    }
}

