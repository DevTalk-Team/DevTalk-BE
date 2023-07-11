package com.devtalk.consultation.consultationservice.consultation.application.port.out.repository;

import com.devtalk.consultation.consultationservice.consultation.domain.consultation.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepo extends JpaRepository<Consultation, Long> {
}
