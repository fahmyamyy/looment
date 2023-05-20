package com.looment.authservice.configs;

import com.looment.authservice.aop.TimeLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoggerConfig implements WebMvcConfigurer {

    @Autowired
    private TimeLogger timeLogger;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timeLogger);
    }
}
