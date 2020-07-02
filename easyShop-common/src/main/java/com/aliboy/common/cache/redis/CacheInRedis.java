package com.aliboy.common.cache.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis缓存的工具类 <br>
 *
 * @author @author xiquee
 * @date 2018-11-09 10:16:00
 * 作缓存层 封装，所以与redis相关的，业务层不可见，全部成为public.
 */
@Component
public class CacheInRedis {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static CacheInRedis cacheInRedis;

    final static Logger logger = LoggerFactory.getLogger(CacheInRedis.class);

    @PostConstruct
    public void init() {
        cacheInRedis = this;
        cacheInRedis.redisTemplate = this.redisTemplate;
    }

    public static void setHashCache(String k, String f, final Object value) {
        cacheInRedis.redisTemplate.opsForHash().put(k, f, value);
    }

    public static Object getHashCache(String k, String f) {
        return cacheInRedis.redisTemplate.opsForHash().get(k, f);
    }

    public static void setCache(String k, Object v, long second) {
        cacheInRedis.redisTemplate.opsForValue().set(k, v, second, TimeUnit.SECONDS);
    }

    public static String getCache(String key) {
        Object o = cacheInRedis.redisTemplate.opsForValue().get(key);
        if (o == null) {
            return null;
        }
        return o.toString();
    }

    public static Object getCacheObj(String key) {
        return cacheInRedis.redisTemplate.opsForValue().get(key);
    }

    public static Long getRemainingTime(String key) {
        return cacheInRedis.redisTemplate.getExpire(key);
    }

    public static boolean exists(String key) {
        return cacheInRedis.redisTemplate.hasKey(key);
    }

    public static Boolean delete(String key) {
        return cacheInRedis.redisTemplate.delete(key);
    }

    public static Long hashDelete(String k, String... f) {
        return cacheInRedis.redisTemplate.opsForHash().delete(k, f);
    }

    public static Set<String> getKeys(String key) {
        return cacheInRedis.redisTemplate.keys(key);
    }


    public static List<Object> getHashList(String key) {
        return cacheInRedis.redisTemplate.opsForHash().values(key);
    }

    public static Map<Object, Object> getHashAll(String key) {
        return cacheInRedis.redisTemplate.opsForHash().entries(key);
    }

    public static long queuePush(String k, Object... v) {
        return cacheInRedis.redisTemplate.opsForList().rightPushAll(k, v);
    }

    public static Object queuePop(String k) {
        return cacheInRedis.redisTemplate.opsForList().leftPop(k);
    }

    public static Long queueSize(String k) {
        return cacheInRedis.redisTemplate.opsForList().size(k);
    }

    public static boolean queueRemove(String k, Object v) {
        return 1 == cacheInRedis.redisTemplate.opsForList().remove(k, 1, v);
    }

    public static DataType getDataType(String key) {
        return cacheInRedis.redisTemplate.type(key);
    }

    public static boolean lock(String key, int timeout) {
        if (isAvailable()) {
            String threadId = String.valueOf(Thread.currentThread().getId());
            if (cacheInRedis.redisTemplate.hasKey(key)) {
                if (threadId.equals(cacheInRedis.redisTemplate.opsForValue().get(key))) {
                    logger.info(key + " LOCKED REENTER BY " + threadId);
                    return Boolean.TRUE;
                }
            }
            if (cacheInRedis.redisTemplate.opsForValue().setIfAbsent(key, threadId, timeout, TimeUnit.SECONDS)) {
                logger.info(key + " LOCKED BY " + threadId);
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public static boolean unlock(String key) {
        if (isAvailable()) {
            if (!cacheInRedis.redisTemplate.hasKey(key) || cacheInRedis.redisTemplate.delete(key)) {
                logger.info(key + " UNLOCKED ");
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }


    public static boolean isAvailable() {
//        return cacheInRedis.redisTemplate.isExposeConnection();
        return true;
    }


}
