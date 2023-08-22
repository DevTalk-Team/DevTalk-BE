package com.devtalk.payment.paymentservice.adapter.in.web.dto;

import lombok.*;

// 공통으로 반환 할 수 있으므로 global >> Respns...
@AllArgsConstructor
@Data
public class PaymentOutput<T> {
    // 결제 요청에 대한 응답으로 쓸 수 있도록 ModelMapper가 이 형태로 PaymentRes를 mapping해줄 예정
    private String code;
    private String message;
    private T result;
}
