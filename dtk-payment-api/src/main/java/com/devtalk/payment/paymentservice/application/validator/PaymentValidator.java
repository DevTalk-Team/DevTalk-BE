package com.devtalk.payment.paymentservice.application.validator;

import com.devtalk.payment.global.code.ErrorCode;
import com.devtalk.payment.global.error.exception.IncorrectException;
import com.devtalk.payment.paymentservice.application.port.in.dto.PaymentReq;
import com.devtalk.payment.paymentservice.application.port.out.repository.ConsultationRepo;
import com.devtalk.payment.paymentservice.application.port.out.repository.PaymentQueryableRepo;
import com.devtalk.payment.paymentservice.application.port.out.repository.PaymentRepo;
import com.devtalk.payment.paymentservice.domain.payment.Payment;
import com.devtalk.payment.paymentservice.domain.payment.PaymentStatus;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.response.IamportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.devtalk.payment.paymentservice.application.port.in.dto.PaymentReq.*;

@Service
@RequiredArgsConstructor
public class PaymentValidator {
//    private final IamportClient iamportClient;
    private final ConsultationRepo consultationRepo;
    private final PaymentRepo paymentRepo;

    public void validateIsItPaid(Payment payment) {
        if (payment.getStatus() != PaymentStatus.PAID) {
            throw new IncorrectException(ErrorCode.NOT_PAID_CONSULTATION);
        }
    }
}
