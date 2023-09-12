package com.devtalk.product.productservice.product.application.service.product;

import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductReservedDetailsReq;
import com.devtalk.product.productservice.product.application.port.in.product.ReserveUseCase;
import com.devtalk.product.productservice.product.application.port.out.repository.ProductRepo;
import com.devtalk.product.productservice.product.application.port.out.repository.ReservedProductRepo;
import com.devtalk.product.productservice.product.domain.product.Product;
import com.devtalk.product.productservice.product.domain.product.ProductReservedDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReserveService implements ReserveUseCase {
    private final ProductRepo productRepo;
    private final ReservedProductRepo reservedProductRepo;

    @Override
    public void reserveProduct(ProductReservedDetailsReq productReservedDetailsReq) {

        // Validate input data
        if (productReservedDetailsReq.getConsultantId() == null) {
            throw new IllegalArgumentException("Consultant ID cannot be null");
        }

        if (productReservedDetailsReq.getConsulterId() == null) {
            throw new IllegalArgumentException("Consulter ID cannot be null");
        }

        if (productReservedDetailsReq.getReservationAt() == null) {
            throw new IllegalArgumentException("Reservation time cannot be null");
        }

        Optional<Product> optionalProduct = productRepo.findByConsultantIdAndReservationAt(
                productReservedDetailsReq.getConsultantId(),
                productReservedDetailsReq.getReservationAt()
        );

        // Check if a Product with the given consultant id and reservation time exists
        if (!optionalProduct.isPresent()) {
            throw new NoSuchElementException("No Product found with the given criteria");
        }

        Product product = optionalProduct.get();

        // Check if the consultant id of the found Product is not null
        if (product.getConsultant().getId() == null) {
            throw new IllegalStateException("The consultant id of the found Product is unexpectedly null");
        }

        ProductReservedDetails productReservedDetails = ProductReservedDetails.builder()
                .product(product)
                .consulterId(productReservedDetailsReq.getConsulterId())
                .price(productReservedDetailsReq.getPrice())
                .reservedProceedType(productReservedDetailsReq.getReservedProceedType())
                .region(productReservedDetailsReq.getRegion())
                .build();

        reservedProductRepo.save(productReservedDetails);
    }
}
