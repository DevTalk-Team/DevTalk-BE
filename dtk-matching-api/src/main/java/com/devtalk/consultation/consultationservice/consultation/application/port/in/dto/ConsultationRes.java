package com.devtalk.consultation.consultationservice.consultation.application.port.in.dto;

import com.devtalk.consultation.consultationservice.consultation.domain.consultation.*;
import com.devtalk.consultation.consultationservice.global.vo.Money;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class ConsultationRes {

    @Builder(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ConsultationSearchRes {
        private Long id;
        private Long productId;
        private Long consulterId;
        private String consulterName;
        private Long consultantId;
        private String consultantName;
        private Long paymentId;
        private ConsultationDetails consultationDetails;
        private ProcessStatus status;
        private Integer cost;

        public static ConsultationSearchRes of(Consultation consultation) {
            return ConsultationSearchRes.builder()
                    .id(consultation.getId())
                    .productId(consultation.getProductId())
                    .consulterId(consultation.getConsulterId())
                    .consulterName(consultation.getConsulterName())
                    .consultantId(consultation.getConsultantId())
                    .consultantName(consultation.getConsultantName())
                    .paymentId(consultation.getPaymentId())
                    .consultationDetails(consultation.getConsultationDetails())
                    .status(consultation.getStatus())
                    .cost(consultation.getCost().getAmount().intValue())
                    .build();
        }
    }
}
