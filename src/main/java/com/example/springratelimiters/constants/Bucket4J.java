package com.example.springratelimiters.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Bucket4J {
    public static final long DEFAULT_CAPACITY = 3L;
    public static final long REFILL_COUNT = 10L;
    public static final long REFILL_PERIOD_IN_MINUTES = 1L;
}
