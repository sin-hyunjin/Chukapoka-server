package com.chukapoka.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private Environment _env;
    
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:8080", _env.getProperty("DOMAIN_URL"))
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}

