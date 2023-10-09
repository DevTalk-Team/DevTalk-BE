package com.devtalk.payment.paymentservice.application.validator;

import com.devtalk.payment.global.code.ErrorCode;
import com.devtalk.payment.global.error.exception.IncorrectException;
import com.devtalk.payment.paymentservice.domain.payment.Payment;
import com.devtalk.payment.paymentservice.domain.payment.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentValidator {

    public void validateItIsPaid(Payment payment) {
        if (payment.getStatus() != PaymentStatus.PAID) {
            throw new IncorrectException(ErrorCode.NOT_PAID_CONSULTATION);
        }
    }
}
