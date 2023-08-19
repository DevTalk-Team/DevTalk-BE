package com.devtalk.payment.paymentservice.application.port.in;

import com.devtalk.payment.paymentservice.domain.consultation.Consultation;

public interface RefundUseCase {
    String getEmailHtmlConsultationRefundInfo(Consultation consultation);
}
