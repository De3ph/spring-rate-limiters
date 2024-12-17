package com.example.springratelimiters.contoller;

import com.example.springratelimiters.annotations.RateLimited;
import com.example.springratelimiters.service.RateLimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foo")
@RequiredArgsConstructor
public class FooController {
    private final RateLimitService rateLimitService;

    @RateLimited(value = "X-ID")
    @GetMapping
    public ResponseEntity<String> foo() {
        return new ResponseEntity<>("Foo", HttpStatus.OK);
    }
}
