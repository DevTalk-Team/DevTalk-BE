package com.devtalk.payment.paymentservice.application.port.in;

import com.devtalk.payment.paymentservice.application.port.in.dto.PaymentReq;
import com.devtalk.payment.paymentservice.application.port.in.dto.PaymentRes;
import com.devtalk.payment.paymentservice.domain.payment.Payment;
import com.siot.IamportRestClient.response.IamportResponse;

import static com.devtalk.payment.paymentservice.application.port.in.dto.PaymentReq.*;
import static com.devtalk.payment.paymentservice.application.port.in.dto.PaymentRes.*;

public interface PaymentUseCase {
    String getToken();

    String getPaymentLink(String token, Long consultationId);

    // 결제 요청 폼 초기화
    PaymentServiceReq requestPaymentForm(Long consultationId);

    // 결제 요청 서비스
    void requestPayment(Long consultationId);

    PaymentSearchRes searchPaymentInfo(Long consultationId);

    // 결제 검증
    IamportResponse<com.siot.IamportRestClient.response.Payment> paymentByCallback(PaymentCallbackReq paymentCallbackReq);
}
