package com.devtalk.product.productservice.product.application.port.out.repository;


import com.devtalk.product.productservice.product.domain.member.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConsultantRepo extends JpaRepository<Consultant, Long> {
    Optional<Consultant> findById(Long id);
}