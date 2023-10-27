package com.devtalk.product.productservice.product.application.port.out.repository;


import com.devtalk.product.productservice.product.domain.product.Product;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductQueryableRepo {
    Optional <Product> findByConsultantIdAndReservationAt(Long consultantId, LocalDateTime reservationAt);

    List<Product> findByConsultantIdAndDate(Long consultantId, LocalDateTime date);
}
