package com.devtalk.product.productservice.product.application.port.out.repository;

import com.devtalk.product.productservice.product.domain.member.Consulter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConsulterRepo extends JpaRepository<Consulter, Long> {
    Optional<Consulter> findById(Long id);
}