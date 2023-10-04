package com.devtalk.product.productservice.product.application.port.out.dto;

import com.devtalk.product.productservice.product.domain.product.Product;
import com.devtalk.product.productservice.product.domain.product.ProductProceedType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class ProductInfoRes {
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ProductSearchRes {
        private Long consultantId;
        private LocalDateTime reservationAT;
        private ProductProceedType productProceedType;
        private String reservationStatus;
        private Integer F2F_Cost;
        private Integer NF2F_Cost;

        public static ProductSearchRes of(Product product){
            return ProductSearchRes.builder()
                    .consultantId(product.getConsultantId())
                    .reservationAT(product.getReservationAt())
                    .productProceedType(product.getProductProceedType())
                    .reservationStatus(product.getStatus())
                    .F2F_Cost(product.getF2FCost())
                    .NF2F_Cost(product.getNF2FCost())
                    .build();
        }
    }
}
