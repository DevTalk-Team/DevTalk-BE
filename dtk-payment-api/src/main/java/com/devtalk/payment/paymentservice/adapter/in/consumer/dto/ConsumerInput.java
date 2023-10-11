package com.devtalk.payment.paymentservice.adapter.in.consumer.dto;

import com.devtalk.payment.paymentservice.domain.consultation.Consultation;
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
        private Long consultantId;
        private ConsultationDetails consultationDetails;
        private ProcessStatus status;
        private Cost cost;

        public static Consultation toEntity(String consultantName, String consulterName, String consulterEmail) {

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
