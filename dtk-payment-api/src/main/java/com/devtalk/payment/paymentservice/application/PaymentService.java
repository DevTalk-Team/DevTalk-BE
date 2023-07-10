package com.devtalk.payment.paymentservice.application;

import com.devtalk.payment.paymentservice.application.port.in.PaymentUseCase;
import com.devtalk.payment.paymentservice.application.port.in.dto.PaymentRes;
import com.devtalk.payment.paymentservice.application.port.out.repository.PaymentQueryableRepo;
import com.devtalk.payment.paymentservice.domain.Payment;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.management.AttributeNotFoundException;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService implements PaymentUseCase {
    private final PaymentQueryableRepo paymentQueryableRepo;

    @Override
    public PaymentRes getPaymentInfoByConsultationId(String consultationId) {
        Payment paymentEntity = paymentQueryableRepo.findByConsultationId(consultationId);

        if (paymentEntity == null) {
            log.info("paymentEntity is null");
            throw new EntityNotFoundException(consultationId);
        }

        PaymentRes paymentRes = new ModelMapper().map(paymentEntity, PaymentRes.class);
        paymentRes.setCode("0301");
        paymentRes.setMessage("조회 성공");
        paymentRes.setResult(paymentEntity);

        log.info(paymentRes.toString());

        return paymentRes;
    }
}
