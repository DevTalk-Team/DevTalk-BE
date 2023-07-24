package com.devtalk.payment.paymentservice.application;

import com.devtalk.payment.paymentservice.application.port.in.RefundUseCase;
import com.devtalk.payment.paymentservice.domain.consultation.Consultation;
import org.springframework.stereotype.Service;

@Service
class RefundService implements RefundUseCase {
    @Override
    public String getEmailHtmlConsultationRefundInfo(Consultation consultation) {
        return null;
    }
}
