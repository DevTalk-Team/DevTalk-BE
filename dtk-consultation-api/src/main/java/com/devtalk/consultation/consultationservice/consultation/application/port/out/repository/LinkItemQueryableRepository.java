package com.devtalk.consultation.consultationservice.consultation.application.port.out.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface LinkItemQueryableRepository {
    boolean existsByProductIdInReservedItem(Long productId);
}
