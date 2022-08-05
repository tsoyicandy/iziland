package com.itiad.iziland.security.config;


import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
public class SpringFoxConfig {

    @Bean
    public Docket izilandApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }


    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger By Sandy")
                .version("1.0")
                .description("Manage Transactions")
                .contact(new Contact("Sandrine Djousse", "http://www.sandy.com", "sandytsoyem@gmail.com"))
                .license("Apache License Version 2.0")
                .build();
    }
}