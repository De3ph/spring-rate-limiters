package com.example.springratelimiters.aspect;

import com.example.springratelimiters.annotations.RateLimited;
import com.example.springratelimiters.exceptions.RateLimitExceededException;
import com.example.springratelimiters.service.RateLimitService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class RateLimiterAspect {
    private RateLimitService<String> rateLimitService;

    public RateLimiterAspect(RateLimitService<String> rateLimitService) {
        this.rateLimitService = rateLimitService;
    }

    @Before("@annotation(rateLimited)")
    public void before(JoinPoint joinPoint, RateLimited rateLimited) throws Exception {

        if (!rateLimitService.tryConsume(rateLimited.value())) {
            throw new RateLimitExceededException("Rate limit exceeded for key: " + rateLimited.value());
        }
    }

}
