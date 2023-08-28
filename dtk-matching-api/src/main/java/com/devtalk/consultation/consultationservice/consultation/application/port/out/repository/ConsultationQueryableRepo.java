package com.devtalk.consultation.consultationservice.consultation.application.port.out.repository;

import com.devtalk.consultation.consultationservice.consultation.domain.consultation.Consultation;

import java.util.Optional;

public interface ConsultationQueryableRepo {
    boolean existsByProductIdInReservedItem(Long productId);

    Optional<Consultation> findByIdWithConsulterId(Long consultationId, Long consulterId);
    Optional<Consultation> findByIdWithConsultantId(Long consultationId, Long consulterId);
}
