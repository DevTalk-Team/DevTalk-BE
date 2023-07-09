package com.devtalk.payment.paymentservice.application;

import com.devtalk.payment.paymentservice.application.port.in.PaymentUseCase;
import com.devtalk.payment.paymentservice.application.port.in.dto.PaymentRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentService implements PaymentUseCase {

    @Override
    public PaymentRes getPaymentInfoByConsultationId(String consultationId) {
        return null;
    }
}
