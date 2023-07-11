package com.devtalk.consultation.consultationservice.consultation.application.port.out.client.dto;

import com.devtalk.consultation.consultationservice.consultation.domain.consultation.ProcessType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class ProductRes {

    @Builder(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ProductSearchRes {
        private Long consultantId;
        private LocalDateTime reservationAT;
        private String processType;
        private String reservationStatus;
        private Integer cost;
    }


}
