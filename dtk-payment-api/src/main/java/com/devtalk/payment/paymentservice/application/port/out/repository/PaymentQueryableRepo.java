package com.devtalk.payment.paymentservice.application.port.out.repository;

import com.devtalk.payment.paymentservice.domain.Payment;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface PaymentQueryableRepo extends Repository<Payment, Long> {
    Payment findByConsultationId(String consultationId);
    Payment findByPaymentId(String paymentId);
    List<Payment> findAll();

}
