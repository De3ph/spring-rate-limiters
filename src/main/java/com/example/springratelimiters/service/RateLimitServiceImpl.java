package com.example.springratelimiters.service;

import com.example.springratelimiters.service.intf.RateLimitService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.local.LocalBucketBuilder;
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import static com.example.springratelimiters.constants.Bucket4J.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class RateLimitServiceImpl implements RateLimitService<String> {
    private final LettuceBasedProxyManager lettuceBasedProxyManager;

    private static final Bandwidth bandwidth = Bandwidth
            .builder()
            .capacity(DEFAULT_CAPACITY)
            .refillIntervally(REFILL_COUNT, Duration.ofMinutes(REFILL_PERIOD_IN_MINUTES))
            .build();

    private Supplier<BucketConfiguration> getConfigSupplier() {
        return () -> BucketConfiguration.builder().addLimit(bandwidth).build();
    }

    private Bucket resolveBucket(String key) {
        Supplier<BucketConfiguration> configSupplier = getConfigSupplier();

        return lettuceBasedProxyManager.builder().build(key, configSupplier);
    }


    @Override
    public boolean tryConsume(String key) {
        Bucket bucket = resolveBucket(key);

        if (bucket.tryConsume(1)) {
            return true;
        } else {
            log.error(HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase());
            return false;
        }

    }

}
