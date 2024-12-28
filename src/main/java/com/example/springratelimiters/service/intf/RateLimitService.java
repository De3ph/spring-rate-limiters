package com.example.springratelimiters.service.intf;


import io.github.bucket4j.Bucket;

public interface RateLimitService<T> {
    boolean tryConsume(T key);
}
