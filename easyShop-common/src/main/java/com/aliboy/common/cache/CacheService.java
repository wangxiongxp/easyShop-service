package com.aliboy.common.cache;

import com.aliboy.common.cache.redis.CacheInRedis;
import org.springframework.data.redis.connection.DataType;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * cache 层进行封装 <br>
 *
 * @author xiquee.com <br>
 * @date: 2018-06-05 14:05:00
 * 业务层唯一可见地缓存服务
 * 。。。。
 * 其实纠结过，用服务接口还是 还是静态方法，服务接口引用之处过于依赖spring boot 注入，
 * 静态方法感觉缓存成了工具，不是一层服务。
 */
public class CacheService {


    public static Object getCacheObj(final String key) {
        return CacheInRedis.getCacheObj(key);
    }

    /**
     * 获取剩下的存活时间
     */
    public static Long getRemainingTime(final String key) {
        return CacheInRedis.getRemainingTime(key);
    }

    /**
     * get the cache value with key
     */
    public static String getCache(final String key) {
        return CacheInRedis.getCache(key);
    }


    /**
     * set cache value with key value and expired second
     */
    public static void setCache(final String key, Object value, long expiredSecond) {
        CacheInRedis.setCache(key, value, expiredSecond);
    }


    /**
     * if the key is exists
     */
    public static boolean exists(final String key) {
        return CacheInRedis.exists(key);
    }

    /**
     * delete the cache with key
     */
    public static Boolean delete(final String key) {
        return CacheInRedis.delete(key);
    }

    /**
     * is cache available
     */
    public static boolean isAvailable() {
        return CacheInRedis.isAvailable();
    }

    /**
     * get all the keys in cache
     */
    public static Set<String> getKeys(final String key) {
        return CacheInRedis.getKeys(key);
    }

    /**
     * get key data type
     */
    public static DataType getDataType(String key) {
        return CacheInRedis.getDataType(key);
    }

    /**
     * get lock
     * lock can re lock by the same thread
     * use temp lock must clean old locks when application startup
     *
     * @param key     the lock name the name must not the same with other locks
     * @param timeout the lock expired time when you don't want want to use unlock or exception
     * @throws CacheLockException when cache is down or thread break down
     */
    public static boolean lock(final String key, final int timeout) {
        return CacheInRedis.lock(key, timeout);
    }

    /**
     * get lock
     * use temp lock must clean old locks when application startup
     * when you release the lock
     *
     * @param key the lock name the name must not the same with other locks
     * @throws CacheLockException when cache is down or thread break down
     */
    public static boolean unlock(final String key) {
        return CacheInRedis.unlock(key);
    }

    /**
     * key holds a hash, retrieve the value associated to the specified field.
     */
    public static Object getHashCache(final String key, final String field) {
        return CacheInRedis.getHashCache(key, field);
    }

    /**
     * Set the specified hash field to the specified value.
     * <p>
     * If key does not exist, a new key holding a hash is created.
     * <p>
     * <b>Time complexity:</b> O(1)
     *
     * @return If the field already exists, and the HSET just produced an update of the value, 0 is
     * returned, otherwise if a new field is created 1 is returned.
     */
    public static void setHashCache(final String key, final String field, final Object value) {
        CacheInRedis.setHashCache(key, field, value);
    }

    /**
     * get has values
     */
    public static List<Object> getHashList(final String key) {
        return CacheInRedis.getHashList(key);
    }

    /**
     * Return all the fields and associated values in a hash.
     * <p>
     * <b>Time complexity:</b> O(N), where N is the total number of entries
     *
     * @return All the fields and values contained into a hash.
     */
    public static Map<Object, Object> getHashAll(String key) {
        return CacheInRedis.getHashAll(key);
    }

    /**
     * Remove the specified field from an hash stored at key.
     *
     * @return If the field was present in the hash it is deleted and 1 is returned, otherwise 0 is
     * returned and no operation is performed.
     */
    public static Long hashDelete(final String key, final String... fields) {
        return CacheInRedis.hashDelete(key, fields);
    }

    /**
     * delete
     */
    public static Boolean delete(Class<?> clz) {
        return delete(clz.getName());
    }

    /**
     *
     * @param clz
     * @param key
     * @param value
     */
    public static void setHashCache(Class<?> clz, String key, final Object value) {
        CacheInRedis.setHashCache(clz.getName(), key, value);
    }

    /**
     * key holds a hash, retrieve the value associated to the specified field.
     */
    public static Object getHashCache(Class<?> clz, final String field) {
        return CacheInRedis.getHashCache(clz.getName(), field);
    }

    /**
     *
     * @param clz
     * @param fields
     */
    public static void hashDelete(Class<?> clz, final String... fields) {
        CacheInRedis.hashDelete(clz.getName(), fields);
    }

    /**
     * queue push
     */
    public static long queuePush(String k, Object... v) {
        return CacheInRedis.queuePush(k, v);
    }

    /**
     * queue pop
     */
    public static Object queuePop(String k) {
        return CacheInRedis.queuePop(k);
    }

    /**
     * queue remove
     */
    public static boolean queueRemove(String k, Object v) {
        return CacheInRedis.queueRemove(k, v);
    }

    /**
     * queue size
     */
    public static long queueSize(String k) {
        return CacheInRedis.queueSize(k);
    }
}
