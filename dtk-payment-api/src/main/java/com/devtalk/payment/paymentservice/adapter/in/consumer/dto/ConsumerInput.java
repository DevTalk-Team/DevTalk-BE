package com.devtalk.payment.paymentservice.adapter.in.consumer.dto;

import com.devtalk.payment.paymentservice.domain.consultation.Consultation;
import com.devtalk.payment.paymentservice.domain.consultation.ProcessStatus;
import com.devtalk.payment.paymentservice.domain.refund.Refund;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ConsumerInput {

    @Getter
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ConsultationInput {
        @JsonProperty("id")
        private Long consultationId;
        private Long productId;
        private Long consulterId;
        private String consulterName;
        private Long consultantId;
        private String consultantName;
        private ConsultationDetails consultationDetails;
        private ProcessStatus status;
        private Cost cost;

        public Consultation toEntity(String consulterEmail) {
            return Consultation.createConsultation(
                    consultationId, consulterId, consulterName, consulterEmail,
                    consultantId, consultantName, consultationDetails.getProceedTypeDescription(),
                    cost.getAmount(), consultationDetails.getReservationDate(), consultationDetails.getReservationTime(), status);
        }

        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        private static class ConsultationDetails{
            @JsonIgnoreProperties(ignoreUnknown = true)
            private ProductProceedType productProceedType;
            @JsonProperty("reservationDate")
            private LocalDate reservationDate;
            @JsonProperty("reservationTime")
            private LocalTime reservationTime;

            public String getProceedTypeDescription() {
                return switch (productProceedType) {
                    case F2F -> "대면 상담";
                    case NF2F -> "비대면 상담";
                    case ALL -> "비대면 및 대면 상담";
                    case NULL -> "구분 없음";
                };
            }
        }

        @Getter
        @NoArgsConstructor
        private enum ProductProceedType {
            F2F, NF2F, ALL, NULL
        }

        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        private static class Cost{
            private Integer amount;
        }
    }
}
