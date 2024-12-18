package com.example.springratelimiters;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FooService {

    @RateLimiter(name = "foo", fallbackMethod = "getFooFallback")
    public String getFoo(){
        return "foo";
    }
    public String getFooFallback(Throwable t) {
        return "rate limit exceeded";
    }
    

}
