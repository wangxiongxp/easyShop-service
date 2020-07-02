package com.aliboy.common.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 *  跨域请求支持
 * 参考：
 * https://spring.io/blog/2015/06/08/cors-support-in-spring-framework#filter-based-cors-support
 * @author xiquee.com <br>
 * @date 2018-11-09 10:16:00
 */

@Configuration
public class MyCorsConfiguration {

    public static final String STR = ",";
    @Value("${logistics.crossDomain.allowedOrigins}")
    private String allowedOrigins;

    @Value("${logistics.crossDomain.allowCredentials}")
    private String allowCredentials;

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(Boolean.valueOf(allowCredentials));
        config.setMaxAge(2592000L);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        String[] origins = getOrigins();
        for (String orign : origins) {
            config.addAllowedOrigin(orign);
        }

        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new org.springframework.web.filter.CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

    private String[] getOrigins() {
        String[] origins = new String[]{"*"};
        if (allowedOrigins != null && allowedOrigins.trim().length() > 0) {
            if (allowedOrigins.indexOf(STR) > 0) {
                origins = allowedOrigins.split(STR);
            } else {
                origins = new String[]{allowedOrigins};
            }
        }
        return origins;
    }
}
