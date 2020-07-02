package com.aliboy.common.security.cache;

import org.apache.shiro.ShiroException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;
import org.apache.shiro.util.Initializable;

/**
 *  ShiroCacheManager <br>
 * @author xiquee.com <br>
 * @date 2018-11-09 10:16:00
 */
public class ShiroCacheManager implements CacheManager, Initializable, Destroyable {
    private boolean cacheManagerImplicitlyCreated = false;

    @Override
    public <K, V> Cache<K, V> getCache(String cacheName) throws CacheException {
        return new ShiroCache(cacheName);
    }

    @Override
    public void init() throws ShiroException {
    }

    @Override
    public void destroy() throws Exception {
        if (this.cacheManagerImplicitlyCreated) {
            this.cacheManagerImplicitlyCreated = false;
        }
    }


}
