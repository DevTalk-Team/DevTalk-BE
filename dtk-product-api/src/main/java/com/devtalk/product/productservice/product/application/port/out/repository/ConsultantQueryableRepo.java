package com.devtalk.product.productservice.product.application.port.out.repository;

import com.devtalk.product.productservice.product.domain.member.Consultant;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsultantQueryableRepo {
    Optional<Consultant> findByConsultantId(Long consultantId);
}
