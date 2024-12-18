package com.example.springratelimiters.controller;

import com.example.springratelimiters.FooService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foo")
@RequiredArgsConstructor
public class FooController {

    private final FooService fooService;

    @GetMapping
    public String foo() {
        return fooService.getFoo();
    }
}
