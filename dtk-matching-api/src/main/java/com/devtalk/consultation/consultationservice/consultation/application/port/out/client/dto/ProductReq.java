package com.devtalk.consultation.consultationservice.consultation.application.port.out.client.dto;

import com.devtalk.consultation.consultationservice.consultation.domain.consultation.ProductProceedType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ProductReq {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ProductSearchReq {
        private Long consultantId;
        private LocalDate reservationDate;
        private LocalTime reservationTime;
        private ProductProceedType productProceedType;
        private String reservationStatus;
        private Integer F2F_Cost;
        private Integer NF2F_Cost;
    }


}
