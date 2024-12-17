package com.example.springratelimiters.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.local.LocalBucketBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.springratelimiters.constants.Bucket4J.*;

@Component
public class RateLimitServiceImpl implements RateLimitService<String> {
    private static final Logger log = LoggerFactory.getLogger(RateLimitServiceImpl.class);

    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    private static final Bandwidth bandwidth = Bandwidth
            .builder()
            .capacity(DEFAULT_CAPACITY)
            .refillIntervally(REFILL_COUNT, Duration.ofMinutes(REFILL_PERIOD_IN_MINUTES))
            .build();

    public boolean tryConsume(String key) {
        Bucket bucket = buckets.computeIfAbsent(key, this::newBucket);

        if (bucket.tryConsume(1)) {
            return true;
        } else {
            log.error(HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase());
            return false;
        }

    }

    private Bucket newBucket(String key) {
        return new LocalBucketBuilder().addLimit(bandwidth).build();
    }

}
