package com.devtalk.product.productservice.product.application.port.in.dto;

import lombok.*;

import java.time.LocalDateTime;

public class ProductReq {
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class RegistProdReq {
        private Long consultantId;
        private String status;
        private LocalDateTime reservationAt;
        private String type;
        private String area;
        private int price;
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class UpdateProdReq {
        private Long consultantId;
        private String status;
        private LocalDateTime reservationAt;
        private String type;
        private String area;
        private int price;
    }
}

