package com.aliboy.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangXiong
 * @Date: Created on 2020-04-15 12:27
 */
@Configuration
public class BeanUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    public BeanUtils() {
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    public static <T> T getBean(Class<T> cls) {
        try {
            return applicationContext != null && cls != null ? applicationContext.getBean(cls) : null;
        } catch (Exception var2) {
            throw new RuntimeException("error occurred when get bean " + cls.getName(), var2);
        }
    }

    public static Object getBean(String beanName) {
        return applicationContext != null && beanName != null && beanName.length() != 0 ? applicationContext.getBean(beanName) : null;
    }

    public static Object getBean(String clsName, ClassLoader classloader) {
        try {
            if (applicationContext != null && clsName != null && clsName.length() != 0) {
                Class cls = classloader == null ? Class.forName(clsName) : Class.forName(clsName, false, classloader);
                return getBean(cls);
            } else {
                return null;
            }
        } catch (Exception var3) {
            throw new RuntimeException("error occurred when get bean " + clsName, var3);
        }
    }

    public static <T> List<T> getBeansByType(Class<T> cls) {
        Map<String, T> map = applicationContext.getBeansOfType(cls);
        List<T> beans = new ArrayList();
        if (map != null && map.size() > 0) {
            beans.addAll(map.values());
        }

        return beans;
    }

    public static String[] getBeanNamesForAnnotation(Class<? extends Annotation> cls) {
        return applicationContext.getBeanNamesForAnnotation(cls);
    }

    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> cls) {
        return applicationContext.getBeansWithAnnotation(cls);
    }

    public static String[] getActiveProfiles() {
        return applicationContext != null ? applicationContext.getEnvironment().getActiveProfiles() : new String[0];
    }
}
