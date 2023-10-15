package com.devtalk.consultation.consultationservice.consultation.application.port.out.repository;

import com.devtalk.consultation.consultationservice.consultation.domain.consultation.Consultation;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.ConsultationCancellation;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.Review;

import java.util.List;
import java.util.Optional;

public interface ConsultationQueryableRepo {
    boolean existsByProductIdInReservedItem(Long productId);

    Optional<Consultation> findByIdWithConsulterId(Long consultationId, Long consulterId);
    Optional<Consultation> findByIdWithConsultantId(Long consultationId, Long consultantId);

    List<Consultation> findAllByConsulterId(Long consulterId);
    List<Consultation> findAllByConsultantId(Long consultantId);

    Optional<ConsultationCancellation> findCancellationByConsultationIdAndConsulterId(Long consultationId, Long consulterId);
    Optional<ConsultationCancellation> findCancellationByConsultationIdAndConsultantId(Long consultationId, Long consultantId);

    List<Review> findAllReviewByConsultantId(Long consultantId);
}
