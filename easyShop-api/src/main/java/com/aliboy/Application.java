package com.aliboy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.Locale;

@EnableAsync
@EnableTransactionManagement
@EnableScheduling
@SpringBootApplication
@MapperScan(basePackages = "com.aliboy.mapper")
@ComponentScan(basePackages = {"com.aliboy", "org.n3r.idworker"})
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        Locale.setDefault(Locale.SIMPLIFIED_CHINESE);
        SpringApplication.run(Application.class,args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        Locale.setDefault(Locale.SIMPLIFIED_CHINESE);
        return application.sources(Application.class);
    }

}




