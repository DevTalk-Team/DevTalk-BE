package com.devtalk.payment.paymentservice.application.port.out.repository;

import com.devtalk.payment.paymentservice.domain.consultation.Consultation;
import org.springframework.data.repository.Repository;

public interface ConsultationQueryableRepo extends Repository<Consultation, Long> {
    Consultation findByConsultationId(String consultationId);
}
