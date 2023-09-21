package com.devtalk.payment.paymentservice.adapter.in.web.dto;

import com.devtalk.payment.paymentservice.domain.consultation.ProcessStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationInput {
    Integer cost;
    String consulter;
    String consultant;
    String consultationType;
    String consulterEmail;
}
