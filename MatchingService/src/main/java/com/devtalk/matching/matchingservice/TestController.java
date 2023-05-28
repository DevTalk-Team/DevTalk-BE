package com.devtalk.matching.matchingservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/matching-service/api")
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "matching-service";
    }
}
