package com.devtalk.consultation.consultationservice.consultation.application.port.out.repository;

public interface ConsultationQueryableRepo {
    boolean existsByProductIdInReservedItem(Long productId);
}
