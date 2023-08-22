package com.devtalk.consultation.consultationservice.consultation.application.port.out.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface LinkItemQueryableRepo {
    boolean existsByProductIdInReservedItem(Long productId);
}
