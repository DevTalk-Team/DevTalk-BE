package com.devtalk.consultation.consultationservice.consultation.application.port.out.repository;

import com.devtalk.consultation.consultationservice.consultation.domain.consultation.Consultation;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface ConsultationQueryableRepo {

    Optional<Consultation> findByConsulterId(Long consulterId);
}
