package com.devtalk.product.productservice.product.application.port.out.repository;

import com.devtalk.product.productservice.product.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    Product findAllById(Long Id);

    List<Product> findAllByConsultantId(Long consultant);
    Optional<Product> findByConsultantIdAndId(Long consultantId, Long id);

}
