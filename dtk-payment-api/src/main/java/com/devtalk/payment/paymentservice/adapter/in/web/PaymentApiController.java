package com.devtalk.payment.paymentservice.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/*
 * 웹 어댑터의 책임
 * - http 요청을 자바 객체로 매핑하기
 * - 권한 검사 (validator에 넘김)
 * - 입력 유효성 검증하기
 * - 입력을 유즈 케이스의 입력 모델로 매핑하기
 * - 유즈 케이스 호출하기
 * - 유즈 케이스의 출력을 http로 매핑하기
 * - http 응답을 반환하기
 * */
@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
class PaymentApiController {
    @PostMapping
    public String requestPayment() {
        return "hello";
    }

    @GetMapping("/{consultationId}")
    public String getPayment(){
        return "hello";
    }

    @DeleteMapping("/{paymentId}")
    public String cancelPayment(){
        return "hello";
    }
}
