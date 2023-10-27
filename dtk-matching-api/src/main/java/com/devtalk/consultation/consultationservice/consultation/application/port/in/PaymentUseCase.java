package com.devtalk.consultation.consultationservice.consultation.application.port.in;

import com.devtalk.consultation.consultationservice.consultation.adapter.in.web.dto.PaymentInput;

public interface PaymentUseCase {
    void paidConsultation(PaymentInput paymentInput);
}
