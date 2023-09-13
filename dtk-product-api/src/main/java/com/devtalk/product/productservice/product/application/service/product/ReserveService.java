package com.devtalk.product.productservice.product.application.service.product;

import com.devtalk.product.productservice.global.error.exception.NotFoundException;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductReservedReq;
import com.devtalk.product.productservice.product.application.port.in.product.ReserveUseCase;
import com.devtalk.product.productservice.product.application.port.out.repository.ProductRepo;
import com.devtalk.product.productservice.product.domain.product.Product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.devtalk.product.productservice.global.error.ErrorCode.NOT_FOUND_PRODUCT;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReserveService implements ReserveUseCase {
    private final ProductRepo productRepo;

    @Transactional
    public void reserveProduct(ProductReservedReq productReserveReq){
        Product product = productRepo.findById(productReserveReq.getProductId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_PRODUCT));
        product.reserveProduct();
    }

//    @Override
//    public void reserveProduct(ProductReservedReq productReserveReq) {
//
////        // Validate input data
////        if (productReservedDetailsReq.getConsultantId() == null) {
////            throw new IllegalArgumentException("Consultant ID cannot be null");
////        }
////
////        if (productReservedDetailsReq.getConsulterId() == null) {
////            throw new IllegalArgumentException("Consulter ID cannot be null");
////        }
////
////        if (productReservedDetailsReq.getReservationAt() == null) {
////            throw new IllegalArgumentException("Reservation time cannot be null");
////        }
//
//        Optional<Product> optionalProduct = productRepo.findByConsultantIdAndProductId(
//                productReservedDetailsReq.getConsultantId(),
//                productReservedDetailsReq.getProductId()
//        );
//
//        // Check if a Product with the given consultant id and reservation time exists
//        if (!optionalProduct.isPresent()) {
//            throw new NoSuchElementException("No Product found with the given criteria");
//        }
//
//        Product product = optionalProduct.get();
//
//        // Check if the consultant id of the found Product is not null
//        if (product.getConsultantId() == null) {
//            throw new IllegalStateException("The consultant id of the found Product is unexpectedly null");
//        }
//
//        ProductReservedDetails productReservedDetails = ProductReservedDetails.builder()
//                .product(product)
//                .consulterId(productReservedDetailsReq.getConsulterId())
//                .price(productReservedDetailsReq.getPrice())
//                .reservedProceedType(productReservedDetailsReq.getReservedProceedType())
//                .region(productReservedDetailsReq.getRegion())
//                .build();
//
//        reservedProductRepo.save(productReservedDetails);
//    }
}
