package com.devtalk.consultation.consultationservice.consultation.application.port.out.client.dto;

import com.devtalk.consultation.consultationservice.consultation.domain.consultation.ProcessMean;
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
        private ProcessMean processMean;
        private String reservationStatus;
        private Integer cost;
    }


}
