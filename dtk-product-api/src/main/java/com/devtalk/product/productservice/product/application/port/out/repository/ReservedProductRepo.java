package com.devtalk.product.productservice.product.application.port.out.repository;

import com.devtalk.product.productservice.product.domain.product.Product;
import com.devtalk.product.productservice.product.domain.product.ReservedDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservedProductRepo extends JpaRepository<ReservedDetails, Long>, ReservedProductQueryableRepo {

    Product findAllById(Long consultationid);

    List<Product> findAllByConsultantId(Long consultant);
    List<ReservedDetails> findAllByMemberId(Long memberId);
}
