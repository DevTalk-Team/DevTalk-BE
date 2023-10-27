package com.devtalk.payment.paymentservice.application.port.in;

import com.devtalk.payment.paymentservice.domain.consultation.Consultation;
import com.devtalk.payment.paymentservice.domain.payment.Payment;

public interface RefundUseCase {
    String getEmailHtmlConsultationRefundInfo(Consultation consultation);

    void cancelPayment(Long consultationId);

    void saveRefundInfo(Payment payment, Consultation consultation);
}
