package com.itiad.iziland.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class ResourceDocumentConfig implements WebMvcConfigurer {
    @Value("${projet.document}")
    private String pathd;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        Path pt = Paths.get(pathd);
        System.out.println("le paths: "+pt.toUri());

        registry.addResourceHandler("/content/**")
                .addResourceLocations(pt.toUri().toString());
    }



}