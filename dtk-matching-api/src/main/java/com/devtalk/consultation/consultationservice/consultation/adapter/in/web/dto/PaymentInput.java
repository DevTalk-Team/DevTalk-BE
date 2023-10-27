package com.devtalk.consultation.consultationservice.consultation.adapter.in.web.dto;

import com.devtalk.consultation.consultationservice.consultation.domain.consultation.Consultation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInput {
    Consultation consultation;
    Long paymentId;
    PaymentStatus status;


    public enum PaymentStatus{
        PAID, READY, CANCELED
    }
}
