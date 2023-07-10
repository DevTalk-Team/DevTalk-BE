package com.devtalk.payment.paymentservice.application.port.in;

import com.devtalk.payment.paymentservice.application.port.in.dto.PaymentRes;

public interface PaymentUseCase {
    PaymentRes getPaymentInfoByConsultationId(String consultationId);
}
