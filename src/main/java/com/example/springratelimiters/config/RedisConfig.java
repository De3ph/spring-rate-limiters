package com.example.springratelimiters.config;

import io.github.bucket4j.distributed.ExpirationAfterWriteStrategy;
import io.github.bucket4j.redis.lettuce.Bucket4jLettuce;
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Value("${redis.host}")
    private String HOST;
    @Value("${redis.port}")
    private int PORT;

    private RedisClient redisClient() {
        String URI = "redis://" + HOST + ":" + PORT;
        return RedisClient.create(URI);
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        return new LettuceConnectionFactory(
                new RedisStandaloneConfiguration(HOST, PORT)
        );
    }

    @Bean
    public LettuceBasedProxyManager lettuceBasedProxyManager() {
        StatefulRedisConnection<String, byte[]> redisConnection = redisClient().connect(RedisCodec.of(StringCodec.UTF8, ByteArrayCodec.INSTANCE));

        return Bucket4jLettuce.casBasedBuilder(redisConnection)
                .expirationAfterWrite(ExpirationAfterWriteStrategy.basedOnTimeForRefillingBucketUpToMax(Duration.ofMinutes(1L)))
                .build();
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate() {
//        RedisTemplate<String, String> template = new RedisTemplate<>();
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(lettuceConnectionFactory());
        return template;
    }

}
