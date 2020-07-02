package com.aliboy.common.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @@authorxiquee.com
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${logistics.crossDomain.allowedOrigins}")
    private String allowedOrigins;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // registry.addInterceptor(new LocaleInterceptor());
        // registry.addInterceptor(new
        // ThemeInterceptor()).addPathPatterns("/**").excludePathPatterns("/admin/**");
        // registry.addInterceptor(new
        // SecurityInterceptor()).addPathPatterns("/secure/*");
        //registry.addInterceptor(new Interceptor()).addPathPatterns("/**");
    }

//    /**
//     * 允许跨域请求
//     * 注意: 这里的跨域请求支持由Spring framework 支持，由interceptor实现，
//     * 这里会有缺陷，http request的执行顺序是servlet-->filter-->interceptor-->controller，
//     * 而Shiro的登录认证和权限控制由filter实现，如果在filter中终止了request请求，那么interceptor中
//     * 的跨域请求实现就无法调用了，跨域请求会失败。然后所有的认证失败都会表现为跨域失败。
//     * 所以对于跨域的认证失败需要单独处理以支持跨域。
//     * @param registry
//     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        String[] origins = new String[]{"*"};
//        if (allowedOrigins != null && allowedOrigins.trim().length() > 0) {
//            if (allowedOrigins.indexOf(",") > 0) {
//                origins = allowedOrigins.split(",");
//            } else {
//                origins = new String[]{allowedOrigins};
//            }
//        }
//        registry.addMapping("/**").allowedHeaders("*")
//                .allowedMethods("*")
//                .allowedOrigins(origins).allowCredentials(true);
//    }
}
