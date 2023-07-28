package com.devtalk.payment.paymentservice.adapter.in.web.dto;

import com.devtalk.payment.paymentservice.application.port.in.dto.PaymentRes;
import com.devtalk.payment.paymentservice.domain.payment.Payment;
import lombok.*;

import java.util.Optional;

import static com.devtalk.payment.paymentservice.application.port.in.dto.PaymentRes.*;

// 공통으로 반환 할 수 있으므로 global >> Respns...
public class PaymentOutput {
    // 결제 요청에 대한 응답으로 쓸 수 있도록 ModelMapper가 이 형태로 PaymentRes를 mapping해줄 예정

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Data
    public static class PaymentInfoOutput{
        private String code;
        private String message;
        private Payment result;

//        public static PaymentInfoOutput of(PaymentSearchRes paymentRes) {
//
//        }

        public PaymentInfoOutput(String code, String message, Payment result) {
            this.code = code;
            this.message = message;
            this.result = result;
        }
    }
}
