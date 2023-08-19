package com.devtalk.payment.paymentservice.application.port.out.repository;

import com.devtalk.payment.paymentservice.domain.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentQueryableRepo{
    Optional<Payment> findByConsultationId(Long consultationId);
}
