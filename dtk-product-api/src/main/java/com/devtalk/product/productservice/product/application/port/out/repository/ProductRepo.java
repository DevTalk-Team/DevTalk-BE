package com.devtalk.product.productservice.product.application.port.out.repository;

import com.devtalk.product.productservice.product.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>, ProductQueryableRepo {

    Product findAllById(Long consultationid);

    List<Product> findAllByConsultantId(Long consultant);
}
