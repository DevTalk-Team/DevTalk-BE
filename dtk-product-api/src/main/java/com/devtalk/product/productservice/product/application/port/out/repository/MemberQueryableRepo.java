package com.devtalk.product.productservice.product.application.port.out.repository;

import com.devtalk.product.productservice.product.domain.member.Consultant;

import java.util.Optional;

public interface MemberQueryableRepo {
    Optional<Consultant> findByConsultantId(Long consultantId);
}
