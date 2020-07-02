package com.aliboy.common.security.cache;

import com.aliboy.common.cache.CacheService;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.*;

/**
 *  redis cache <br>
 * @author xiquee.com <br>
 * @date 2018-11-09 10:16:00
 */
public class ShiroCache<K, V> implements Cache<K, V> {

    private String shiroCacheKey;

    ShiroCache(String cacheName) {
        this.shiroCacheKey = cacheName;
    }

    @Override
    public Object get(Object k) throws CacheException {
        try {
            return CacheService.getHashCache(shiroCacheKey, k.toString());
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Object put(Object k, Object v) throws CacheException {
        try {
            CacheService.setHashCache(shiroCacheKey, k.toString(), v);
            return Boolean.TRUE;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Object remove(Object k) throws CacheException {
        try {
            Object obj = CacheService.getHashCache(shiroCacheKey, k.toString());
            CacheService.hashDelete(shiroCacheKey, k.toString());
            return obj;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void clear() throws CacheException {
        try {
            CacheService.delete(shiroCacheKey);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public int size() {
        try {
            return CacheService.getKeys(shiroCacheKey).size();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Set<K> keys() {
        try {
            Set<String> hashKeys = CacheService.getKeys(shiroCacheKey);
            Set keys = new HashSet();
            hashKeys.stream().forEach(keys::add);
            return keys;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Collection<V> values() {
        try {
            List<Object> hvals = CacheService.getHashList(shiroCacheKey);
            Collection vals = new ArrayList<>();
            hvals.stream().forEach(vals::add);
            return vals;
        } catch (Exception e) {
            throw e;
        }
    }
}
