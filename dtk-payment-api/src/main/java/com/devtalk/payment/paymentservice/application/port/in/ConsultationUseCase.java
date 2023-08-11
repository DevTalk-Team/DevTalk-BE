package com.devtalk.payment.paymentservice.application.port.in;

import com.devtalk.payment.paymentservice.domain.consultation.Consultation;

public interface ConsultationUseCase {
    String getEmailHtmlConsultationPaidInfo(Consultation consultation);

    Consultation searchConsultationInfo(Long consultationId);


}
