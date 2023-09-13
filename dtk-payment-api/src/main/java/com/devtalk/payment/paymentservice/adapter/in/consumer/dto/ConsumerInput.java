package com.devtalk.payment.paymentservice.adapter.in.consumer.dto;

import com.devtalk.payment.paymentservice.domain.consultation.ProcessStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConsumerInput {
    public static class ConsultationInput{
        Long consultationId;

        @JsonProperty("consulter_name")
        String consulter;

        @JsonProperty("consultant_name")
        String consultant;

        @JsonProperty("amount")
        Integer cost;

        ProcessStatus status;
    }
}
