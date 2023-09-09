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
    ProductRepo productRepo;
    ReservedProductRepo reservedProductRepo;
    @Override
    public void reserveProduct(ProductReservedDetailsReq productReservedDetailsReq) {
        Optional<Product> optionalProduct = productRepo.findByConsultantIdAndReservationAt(
                productReservedDetailsReq.getConsultantId(),
                productReservedDetailsReq.getReservationAt()
        );

        Product product = optionalProduct.orElseThrow(() -> new NoSuchElementException("No Product found with the given criteria"));

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
