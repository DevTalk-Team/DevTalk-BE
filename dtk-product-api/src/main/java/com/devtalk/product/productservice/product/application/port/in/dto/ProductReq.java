package com.devtalk.product.productservice.product.application.port.in.dto;

import com.devtalk.product.productservice.product.domain.product.ProductProceedType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ProductReq {
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class RegistProdReq {
        private Long memberId;

        private LocalDate reservationDate;
        private LocalTime reservationTime;
        private ProductProceedType productProceedType;
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ReserveProdReq {
        private Long productId;
        private Long consulterId;
//        private ReservedProceedType reservedProceedType;
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class SearchProdReq {
        private Long memberId;
        private LocalDate reservationDate;
        private LocalTime reservationTime;
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class DeleteProdReq {
        private LocalDate reservationDate;
        private LocalTime reservationTime;
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class UpdateProdReq {
        private LocalDate reservationDate;
        private LocalTime reservationTime;
        private ProductProceedType productProceedType;
    }
}

