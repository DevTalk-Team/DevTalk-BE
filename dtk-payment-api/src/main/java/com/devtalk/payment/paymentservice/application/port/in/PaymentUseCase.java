package com.devtalk.payment.paymentservice.application.port.in;

import com.devtalk.payment.paymentservice.application.port.in.dto.PaymentRes;
import com.devtalk.payment.paymentservice.domain.payment.Payment;

public interface PaymentUseCase {
    Payment searchPaymentInfo(Long consultationId);
}
