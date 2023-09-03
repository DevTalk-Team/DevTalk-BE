package com.devtalk.product.productservice.product.application.port.in.dto;

import com.devtalk.product.productservice.product.domain.product.ProductProceedType;
import com.devtalk.product.productservice.product.domain.product.ReservedProceedType;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ProductReservedDetailsReq {
    private Long consultantId;
    private Long consulterId;
    private LocalDateTime reservationAt;
    private ReservedProceedType reservedProceedType;
    private int price;
    private String region;
}