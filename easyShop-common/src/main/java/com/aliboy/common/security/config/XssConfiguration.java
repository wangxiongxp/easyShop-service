package com.aliboy.common.security.config;

import com.aliboy.common.security.filter.XssFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xiquee.com. <br>
 * @date 2018-11-09 10:16:00
 */
@Configuration
public class XssConfiguration {
    private static final String STR = ",";
    @Value("${logistics.xss.whiteUrls}")
    private String whiteUrls;

    @Bean
    public FilterRegistrationBean xssFilter() {
        return new FilterRegistrationBean<>(new XssFilter(getWhiteUrlList()));
    }

    private List<String> getWhiteUrlList() {
        List<String> whiteUrlList = new ArrayList<>();
        if (whiteUrls != null && whiteUrls.trim().length() > 0) {
            whiteUrls = whiteUrls.trim().toLowerCase();
            if (whiteUrls.indexOf(STR) > 0) {
                String[] urls = whiteUrls.split(STR);
                for (String url : urls) {
                    whiteUrlList.add(url);
                }
            } else {
                whiteUrlList.add(whiteUrls);
            }
        }
        return whiteUrlList;
    }
}
