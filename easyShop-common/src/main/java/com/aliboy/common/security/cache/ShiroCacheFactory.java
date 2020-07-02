package com.aliboy.common.security.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import java.io.Serializable;

/**
 * ShiroCacheFactory <br>
 *
 * @author xiquee.com <br>
 * @date 2018-11-09 10:16:00
 */
public class ShiroCacheFactory {
    private static ShiroCacheFactory instance = new ShiroCacheFactory();
    private static CacheManager cacheManager = createCacheManager();

    /**
     * @return
     */
    public static ShiroCacheFactory getInstance() {

        return instance;
    }

    /**
     *
     */
    private ShiroCacheFactory() {

    }


    public CacheManager getCacheManager() {
        return cacheManager;
    }

    private static CacheManager createCacheManager() {
        //  if no cache config ,will false check later
        ShiroCacheManager rcm = new ShiroCacheManager();
        return rcm;
    }

    /**
     * @return
     */
    public Cache<String, Serializable> getOnlineSessionKeyCache(String cacheName) {
        return getCacheManager().getCache(cacheName);
    }
}
