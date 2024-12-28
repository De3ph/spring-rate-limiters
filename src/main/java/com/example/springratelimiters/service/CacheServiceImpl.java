package com.example.springratelimiters.service;

import com.example.springratelimiters.service.intf.CacheService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Primary
@Component
@AllArgsConstructor
public class CacheServiceImpl implements CacheService<String, String> {

    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String ping() {
        String ping = redisTemplate.opsForValue().get("ping");
        return ping;
    }

    @Override
    public void putToCache(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String getFromCache(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
