package com.devtalk.payment.paymentservice.application.port.out.repository;

import com.devtalk.payment.paymentservice.domain.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long>, PaymentQueryableRepo {
}
