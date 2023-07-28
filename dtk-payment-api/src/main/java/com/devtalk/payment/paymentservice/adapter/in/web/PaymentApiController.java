package com.devtalk.payment.paymentservice.adapter.in.web;

import com.devtalk.payment.paymentservice.adapter.in.web.dto.PaymentInput;
import com.devtalk.payment.paymentservice.adapter.in.web.dto.PaymentOutput;
import com.devtalk.payment.paymentservice.application.port.in.PaymentUseCase;
import com.devtalk.payment.paymentservice.application.port.in.dto.PaymentRes;
import com.devtalk.payment.paymentservice.domain.payment.Payment;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.devtalk.payment.paymentservice.adapter.in.web.dto.PaymentOutput.*;

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
@RequestMapping("/payment")
class PaymentApiController {
    private final PaymentUseCase paymentUseCase;

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @PostMapping
    public ResponseEntity<PaymentOutput> requestPayment(@RequestBody PaymentInput input) {
        // 1. PaymentUseCase의 서비스를 호출해 포트원에 결제 하고자 하는 정보를 보낸다.
        // 2. 호출 받은 서비스는 포트원에서 처리 된 결과를 paymentOutput 형태로 반환한다. (트랜잭션을 적용할것)
        // 3. 결제가 성공했다면 PaymentUseCase의 이메일 서비스를 호출해 사용자에게 이메일을 전송한다

        PaymentOutput paymentOutput = null;

        return ResponseEntity.status(HttpStatus.OK).body(paymentOutput);
    }

    @GetMapping("/{consultationId}")
    public ResponseEntity<PaymentInfoOutput> getPaymentInfo(
            @PathVariable("consultationId") Long consultationId){
        Payment paymentSearchRes = paymentUseCase.searchPaymentInfo(consultationId);

        // PaymentInfoOutput.of(paymentSearchRes);

        PaymentInfoOutput paymentInfoOutput
                = new PaymentInfoOutput("0301", "조회 성공", paymentSearchRes);

        return ResponseEntity.status(HttpStatus.OK).body(paymentInfoOutput);
    }

    @DeleteMapping("/{paymentId}")
    public String cancelPayment(){
        return "hello";
    }
}
