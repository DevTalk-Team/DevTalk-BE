package com.devtalk.product.productservice.product.application.port.in.dto;

import com.devtalk.product.productservice.product.domain.product.ConsultationType;
import com.devtalk.product.productservice.product.domain.product.ReservedType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class ProductReq {
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class RegistProdReq {
        private Long consultantId;
        //private String status;
        private LocalDateTime reservationAt;
        private ConsultationType type;
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ReserveProdReq {
        private Long productId;
        private Long consulterId;
        private ReservedType reservedType;
    }
    public static class SearchListReq {
        private Long consultantId;
    }
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class DeleteProdReq {
        private Long reservedId;
    }
}

