package com.devtalk.product.productservice.product.application.port.in.dto;

import com.devtalk.product.productservice.product.domain.product.ConsultationType;
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
        private String status;
        private LocalDateTime reservationAt;
        private ConsultationType type;
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ReserveProdReq {
        private Long consultantId;
        private Long consulterId;
        private Long productId;
        private String status;
        private LocalDateTime reservationAt;
        private ConsultationType type;
        private Long price;
        private String area;
    }

    public static class SearchListReq {
        private Long consultantId;
    }
}

