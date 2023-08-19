package com.devtalk.payment.paymentservice.adapter.out.persistence;

import com.devtalk.payment.paymentservice.application.port.out.repository.PaymentQueryableRepo;
import com.devtalk.payment.paymentservice.application.port.out.repository.PaymentRepo;
import com.devtalk.payment.paymentservice.domain.payment.Payment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class PaymentQueryRepo implements PaymentQueryableRepo {
    private final EntityManager em;

    @Override
    public Optional<Payment> findByConsultationId(Long consultationId) {
        return Optional.ofNullable(
                    em.createQuery(
                "select p from Payment p " +
                        "where p.consultation = :consultationId", Payment.class)
                        .setParameter("consultationId", consultationId)
                        .getSingleResult()
        );
    }



}
