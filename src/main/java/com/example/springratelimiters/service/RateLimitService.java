package com.example.springratelimiters.service;

public interface RateLimitService<T> {
    boolean tryConsume(T key);
}
