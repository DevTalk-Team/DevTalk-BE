package com.devtalk.payment.paymentservice.application.port.out.repository;

import com.devtalk.payment.paymentservice.domain.Payment;
import org.springframework.data.repository.Repository;

public interface PaymentCommandableRepo extends Repository<Payment, Long> {

}
