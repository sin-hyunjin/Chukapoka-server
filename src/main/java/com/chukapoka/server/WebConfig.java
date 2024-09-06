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

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:8080", "https://chkapoka-client.vercel.app",
                        _env.getProperty("DOMAIN_URL_HTTP"),
                        _env.getProperty("DOMAIN_URL_HTTPS"))
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}