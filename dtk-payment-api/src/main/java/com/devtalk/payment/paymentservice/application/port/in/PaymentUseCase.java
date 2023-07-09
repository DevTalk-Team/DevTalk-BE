package com.devtalk.payment.paymentservice.application.port.in;

import com.devtalk.payment.paymentservice.application.port.in.dto.PaymentRes;

public interface PaymentUseCase {
    public PaymentRes getPaymentInfoByConsultationId(String consultationId);
}
