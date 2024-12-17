package com.example.springratelimiters.config;

import com.example.springratelimiters.config.interceptors.RateLimitInterceptor;
import com.example.springratelimiters.service.RateLimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final RateLimitService rateLimitService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RateLimitInterceptor(rateLimitService));
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
