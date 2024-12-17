package com.example.springratelimiters.exceptions;


import org.springframework.http.HttpStatus;

public class RateLimitExceededException extends RuntimeException {
    public RateLimitExceededException(String key) {
        super(
                new StringBuilder().append(HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase()).append(" for id : ").append(key).toString()
        );
    }
}
