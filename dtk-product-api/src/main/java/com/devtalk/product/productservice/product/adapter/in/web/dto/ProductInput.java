package com.devtalk.product.productservice.product.adapter.in.web.dto;

import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq;
import com.devtalk.product.productservice.product.domain.product.ConsultationType;
import com.devtalk.product.productservice.product.domain.product.ReservedType;
import lombok.*;

import java.time.LocalDateTime;

public class ProductInput {
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class RegistrationInput{

        private Long consultantId;
        private  LocalDateTime reservationAt;
        private ConsultationType type;

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
        private ReservedType reservedType;

        public ProductReq.ReserveProdReq toReq(Long productId) {
            return ProductReq.ReserveProdReq.builder()
                    .productId(productId)
                    .consulterId(consulterId)
                    .reservedType(reservedType)
                    .build();
        }
    }
}
