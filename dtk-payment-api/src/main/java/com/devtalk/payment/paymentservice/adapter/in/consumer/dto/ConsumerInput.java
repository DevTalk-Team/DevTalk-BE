package com.devtalk.payment.paymentservice.adapter.in.consumer.dto;

import com.devtalk.payment.paymentservice.domain.consultation.Consultation;
import com.devtalk.payment.paymentservice.domain.consultation.ProcessStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class ConsumerInput {

    @Getter
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
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
            return Consultation.builder()
                    .merchantId(null)
                    .consulterId(consulterId)
                    .consulterName(consulterName)
                    .consulterEmail(consulterEmail)
                    .consultantId(consultantId)
                    .consultantName(consultantName)
                    .consultationType(consultationDetails.getProductProceedType())
                    .cost(cost.getAmount())
                    .consultationAt(consultationDetails.getReservationAt())
                    .processStatus(com.devtalk.payment.paymentservice.domain.consultation.ProcessStatus.APPROVED)
                    .build();
        }

        @Getter
        @Builder
        private static class ConsultationDetails{
            private String productProceedType;
            private LocalDateTime reservationAt;
        }

        @Getter
        @Builder
        private static class Cost{
            private Integer amount;
        }

        @Getter
        public enum ProcessStatus {
            ACCEPTED, CANCELED
        }
    }
}
