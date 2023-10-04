package com.devtalk.consultation.consultationservice.consultation.application.port.out.client.dto;

import com.devtalk.consultation.consultationservice.consultation.domain.consultation.ProductProceedType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class ProductRes {

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
    }


}
