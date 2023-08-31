package com.devtalk.consultation.consultationservice.consultation.application.port.in;

public interface AcceptConsultationUseCase {

    void acceptConsultation(Long consultantId, Long consultationId);
}
