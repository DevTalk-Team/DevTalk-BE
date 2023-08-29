package com.devtalk.product.productservice.product.adapter.in.web.dto;

import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq;
import com.devtalk.product.productservice.product.domain.product.Product;
import com.devtalk.product.productservice.product.domain.product.ProductProceedType;
import com.devtalk.product.productservice.product.domain.product.ReservedProceedType;
import lombok.*;

import java.time.LocalDateTime;

public class ProductInput {
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class RegistrationInput{

        //private Long consultantId;
        private  LocalDateTime reservationAt;
        private ProductProceedType type;

        public ProductReq.RegistProdReq toReq(Long consultantId){
            return ProductReq.RegistProdReq.builder()
                    .consultantId(consultantId)
                    .reservationAt(reservationAt)
                    .type(type)
                    .build();
        }
    }
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ReservationInput {
        private Long productId;
        private Long consulterId;
        private ReservedProceedType reservedProceedType;

        public ProductReq.ReserveProdReq toReq(Long productId) {
            return ProductReq.ReserveProdReq.builder()
                    .productId(productId)
                    .consulterId(consulterId)
                    .reservedProceedType(reservedProceedType)
                    .build();
        }
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class UpdateInput{

        private Long productId;
        private LocalDateTime reservationAt;
        private ProductProceedType type;

        public ProductReq.UpdateProdReq toReq(Long productId){
            return ProductReq.UpdateProdReq.builder()
                    .productId(productId)
                    .reservationAt(reservationAt)
                    .type(type)
                    .build();
        }
    }
}
