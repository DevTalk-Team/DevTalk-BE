package com.devtalk.product.productservice.product.application.port.in.dto;

import com.devtalk.product.productservice.product.domain.product.ProductProceedType;
import com.devtalk.product.productservice.product.domain.product.ReservedProceedType;
import lombok.*;

import java.time.LocalDateTime;

public class ProductReq {
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class RegistProdReq {
        private Long consultantId;
        //private String status;
        private LocalDateTime reservationAt;
        private ProductProceedType type;
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ReserveProdReq {
        private Long productId;
        private Long consulterId;
        private ReservedProceedType reservedProceedType;
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class DeleteProdReq {
        private Long reservedId;
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class UpdateProdReq {
        private Long productId;
        private LocalDateTime reservationAt;
        private ProductProceedType type;
    }
}

