package com.aliboy.web.swagger;

import com.aliboy.common.utils.SwaggerUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {

//    http://localhost:8088/swagger-ui.html     原路径
//    http://localhost:8088/doc.html     原路径

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    .select()
                    .apis(SwaggerUtils.basePackages(new String[]{"com.aliboy.api", "com.aliboy.common.api"}))
                    .paths(PathSelectors.any())
                    .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("easyShop电商平台接口api")
                .contact(new Contact("aliboy",
                        "https://www.aliboy.com",
                        "abc@aliboy.com"))
                .description("专为easyShop提供的api文档")
                .version("1.0.1")
                .termsOfServiceUrl("https://www.aliboy.com")
                .build();
    }

}
