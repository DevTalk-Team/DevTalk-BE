package com.devtalk.product.productservice.product.application.port.out.dto;

import com.devtalk.product.productservice.product.domain.product.Product;
import com.devtalk.product.productservice.product.domain.product.ProductProceedType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ProductInfoRes {
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ProductSearchRes {
        private Long consultantId;
        private LocalDate reservationDate;
        private LocalTime reservationTime;
        private ProductProceedType productProceedType;
        private String reservationStatus;
        private Integer F2F_Cost;
        private Integer NF2F_Cost;

        public static ProductSearchRes of(Product product){
            return ProductSearchRes.builder()
                    .consultantId(product.getConsultantId())
                    .reservationDate(product.getReservationDate())
                    .reservationTime(product.getReservationTime())
                    .productProceedType(product.getProductProceedType())
                    .reservationStatus(product.getStatus())
                    .F2F_Cost(product.getF2FCost())
                    .NF2F_Cost(product.getNF2FCost())
                    .build();
        }
    }
}
