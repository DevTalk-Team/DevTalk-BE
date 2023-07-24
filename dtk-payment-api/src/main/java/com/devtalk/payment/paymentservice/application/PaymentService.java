package com.devtalk.payment.paymentservice.application;

import com.devtalk.payment.global.error.ErrorCode;
import com.devtalk.payment.global.error.exception.NotFoundException;
import com.devtalk.payment.paymentservice.application.port.in.EmailUseCase;
import com.devtalk.payment.paymentservice.application.port.in.PaymentUseCase;
import com.devtalk.payment.paymentservice.application.port.out.repository.PaymentRepo;
import com.devtalk.payment.paymentservice.domain.payment.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService implements PaymentUseCase {
    private final PaymentRepo paymentRepo;
    private final EmailUseCase emailUseCase;

    @Override
    public Payment searchPaymentInfo(Long consultationId) {
        return paymentRepo.findByConsultationId(consultationId)
                .orElseThrow(()-> new NotFoundException(ErrorCode.NOT_FOUND_CONSULTATION));
    }
}
