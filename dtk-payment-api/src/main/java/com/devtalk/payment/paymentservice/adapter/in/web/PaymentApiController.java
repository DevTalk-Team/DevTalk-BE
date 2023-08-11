package com.devtalk.payment.paymentservice.adapter.in.web;

import com.devtalk.payment.paymentservice.adapter.in.web.dto.PaymentInput;
import com.devtalk.payment.paymentservice.adapter.in.web.dto.PaymentOutput;
import com.devtalk.payment.paymentservice.application.port.in.PaymentUseCase;
import com.devtalk.payment.paymentservice.application.port.in.dto.PaymentReq;
import com.devtalk.payment.paymentservice.application.port.in.dto.PaymentRes;
import com.devtalk.payment.paymentservice.domain.payment.Payment;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.response.IamportResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.devtalk.payment.paymentservice.adapter.in.web.dto.PaymentOutput.*;
import static com.devtalk.payment.paymentservice.application.port.in.dto.PaymentReq.*;
import static com.devtalk.payment.paymentservice.application.port.in.dto.PaymentRes.*;

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
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/payment")
class PaymentApiController {
    private final PaymentUseCase paymentUseCase;

    // 결제할 상품 조회
    @GetMapping("/{consultationId}")
    public String payment(@PathVariable("consultationId") Long consultationId, Model model){
        PaymentServiceReq paymentServiceReq = paymentUseCase.requestPaymentForm(consultationId);
        model.addAttribute("paymentServiceReq", paymentServiceReq);

        return "index";
    }

    // 결제 버튼 누를시 요청
    @PostMapping("/{consultationId}")
    public ResponseEntity<PaymentOutput> requestPayment(@PathVariable("consultationId") Long consultationId) {
        // 포트원에 결제 요청
        paymentUseCase.requestPayment(consultationId);

        // 반환 값
        Payment paymentInfo = paymentUseCase.searchPaymentInfo(consultationId);
        PaymentRequestRes paymentRequestRes = new PaymentRequestRes(paymentInfo.getId(), paymentInfo.getPaymentUid());
        PaymentOutput paymentOutput
                = new PaymentOutput("0300", "결제 성공", paymentRequestRes);
        return ResponseEntity.status(HttpStatus.OK).body(paymentOutput);
    }

    @PostMapping
    public ResponseEntity<IamportResponse<com.siot.IamportRestClient.response.Payment>>
    validationPayment(@RequestBody PaymentCallbackReq request) {
        IamportResponse<com.siot.IamportRestClient.response.Payment> iamportResponse =
                paymentUseCase.paymentByCallback(request);
        log.info("결제 응답={}", iamportResponse.getResponse().toString());

        return ResponseEntity.status(HttpStatus.OK).body(iamportResponse);
    }

    @GetMapping("/{consultationId}/info")
    public ResponseEntity<PaymentOutput> getPaymentInfo(
            @PathVariable("consultationId") Long consultationId){
        Payment paymentSearchRes = paymentUseCase.searchPaymentInfo(consultationId);

        // PaymentInfoOutput.of(paymentSearchRes);

        PaymentOutput paymentOutput
                = new PaymentOutput("0301", "조회 성공", paymentSearchRes);

        return ResponseEntity.status(HttpStatus.OK).body(paymentOutput);
    }

    @DeleteMapping("/{paymentId}")
    public String cancelPayment(){
        return "hello";
    }

    // test
    @GetMapping("/success-payment")
    public String successPaymentPage() {
        return "success-payment";
    }

    @GetMapping("/fail-payment")
    public String failPaymentPage() {
        return "fail-payment";
    }
}
