package com.example.springratelimiters.contoller;

import com.example.springratelimiters.service.intf.CacheService;
import com.example.springratelimiters.service.intf.RateLimitService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foo")
@AllArgsConstructor
public class FooController {
    private CacheService<String, String> cacheService;
    private RateLimitService rateLimitService;


    @GetMapping("/ping")
    public ResponseEntity<String> pingFromCache() {
        return ResponseEntity.ok(cacheService.ping());
    }

    @GetMapping
    public ResponseEntity<String> foo() {
        if (!rateLimitService.tryConsume("foo")) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase(), HttpStatus.TOO_MANY_REQUESTS);
        }
        return new ResponseEntity<>("Foo", HttpStatus.OK);
    }
}
