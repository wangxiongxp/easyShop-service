package com.aliboy;

import com.aliboy.common.constants.SystemConstant;
import com.aliboy.common.scheduler.RunnableBean;
import com.aliboy.common.utils.BeanUtils;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.util.List;
import java.util.concurrent.*;

public class ApplicationStartupListener implements ApplicationListener<ApplicationReadyEvent> {

    final static Logger logger = LoggerFactory.getLogger(ApplicationStartupListener.class);

    private ExecutorService cachedThreadPool;

    ApplicationStartupListener() {
        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("runnable-beans-pool-%d").build();
        cachedThreadPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>(),factory);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        logger.info(" ##############  Application is Ready  ########### ");
        String[] activeProfiles = BeanUtils.getActiveProfiles();
        System.setProperty(SystemConstant.SYSTEM_ACTIVE_PROFILES, StringUtils.join(activeProfiles, SystemConstant.MESSAGE_SPILT));
        logger.info("Active Profile :" + activeProfiles[0]);
        List<RunnableBean> beans = BeanUtils.getBeansByType(RunnableBean.class);
        if (beans != null && beans.size() > 0) {
            for (RunnableBean bean : beans) {
                logger.debug("Ready to start thread " + bean.getName());
                cachedThreadPool.execute(bean);
            }
        }
    }
}
