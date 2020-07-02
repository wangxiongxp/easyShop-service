package com.aliboy.common.cache;

import com.aliboy.common.scheduler.RunnableBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Function: CacheInitiator <br>
 * TODO 清理缓存，然后初绐化需要的基本数据缓存。
 *
 * @author: siqishangshu <br>
 * @date: 2018-12-28 13:38:00
 */
@Component
public class CacheInitiator implements RunnableBean {

    private Logger logger = LoggerFactory.getLogger(CacheInitiator.class);
    private static String SESSION = "shiro";
    private static String RETAIN = "retain";

    @Override
    public String getName() {
        return "cache cleaner ";
    }

    @Override
    public void stopSignal() {

    }

    @Override
    public void run() {
        Set<String> keySet = CacheService.getKeys("*");
        keySet.stream().forEach(keys -> {
            if (!keys.contains(SESSION) && !keys.contains(RETAIN)) {
                CacheService.delete(keys);
            }
        });
        logger.info(getName() + " clean done");
    }
}
