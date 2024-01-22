package com.chukapoka.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private Environment _env;
    
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:8080"
                , _env.getProperty("DOMAIN_URL_HTTP")
                , _env.getProperty("DOMAIN_URL_HTTPS")
                // , _env.getProperty("AWS_DOMAIN_URL_HTTP")
                // , _env.getProperty("AWS_DOMAIN_URL_HTTPS")
                )
                .allowedMethods("GET"
                , "POST", "PUT", "DELETE", "OPTIONS");
    }
}

