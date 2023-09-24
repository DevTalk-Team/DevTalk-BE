package com.devtalk.consultation.consultationservice.consultation.adapter.in.web;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@Tag(name = "매칭 서비스", description = "데브톡 - 매칭 서비스 REST API")
@EnableDiscoveryClient
@RestController
@Slf4j
@RequestMapping("/matching")
@RequiredArgsConstructor
public class MatchingApiController {
    private final Environment env;


    // 상담 거절, 결제 전 취소, 결제 후 취소는 모두 이 api로
    @GetMapping("/welcome")
    public String welcome(){
        return ("hello");
    }
    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in matching Service"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")
                + ", port(server.port)=" + env.getProperty("server.port")
                + ", token secret=" + env.getProperty("token.secret")
                + ", token expiration time=" + env.getProperty("token.expiration_time"));
    }

}
