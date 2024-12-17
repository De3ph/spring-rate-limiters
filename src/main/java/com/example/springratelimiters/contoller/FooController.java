package com.example.springratelimiters.contoller;

import com.example.springratelimiters.service.RateLimitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foo")
public class FooController {
    private RateLimitService rateLimitService;

    public FooController(RateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
    }

    @GetMapping
    public ResponseEntity<String> foo() {
        if (!rateLimitService.tryConsume("foo")) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase(), HttpStatus.TOO_MANY_REQUESTS);
        }
        return new ResponseEntity<>("Foo", HttpStatus.OK);
    }
}
