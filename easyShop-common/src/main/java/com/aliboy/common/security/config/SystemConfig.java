package com.aliboy.common.security.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

/**
 *  SystemConfig <br>
 * @author  @author xiquee
 * @date 2018-11-09 10:16:00
 */
@Configuration
public class SystemConfig {
    //最大10MB
    private static long MAX_FILE_SIZE = 10 * 1024 * 1024;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofBytes(MAX_FILE_SIZE));
        return factory.createMultipartConfig();
    }
}
