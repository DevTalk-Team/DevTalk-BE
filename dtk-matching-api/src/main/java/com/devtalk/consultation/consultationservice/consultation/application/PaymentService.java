package com.devtalk.consultation.consultationservice.consultation.application;

import com.devtalk.consultation.consultationservice.consultation.adapter.in.web.dto.PaymentInput;
import com.devtalk.consultation.consultationservice.consultation.application.port.in.PaymentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentService implements PaymentUseCase {
    @Override
    public void paidConsultation(PaymentInput paymentInput) {
        if (paymentInput.getStatus() == PaymentInput.PaymentStatus.PAID);
    }
}
