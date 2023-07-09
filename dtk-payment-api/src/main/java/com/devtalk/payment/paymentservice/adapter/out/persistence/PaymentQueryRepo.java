package com.devtalk.payment.paymentservice.adapter.out.persistence;

import com.devtalk.payment.paymentservice.application.port.out.repository.PaymentQueryableRepo;
import com.devtalk.payment.paymentservice.domain.Payment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
class PaymentQueryRepo implements PaymentQueryableRepo{
    private final EntityManager em;

    @Override
    public Payment findByConsultationId(String consultationId) {
        return em.createQuery("select p from Payment p " +
                        "where p.consultationId = :consultationId", Payment.class)
                .setParameter("consultationId", consultationId)
                .getSingleResult();
    }

    @Override
    public Payment findByPaymentId(String paymentId) {
        return em.find(Payment.class, paymentId);
    }

    @Override
    public List<Payment> findAll() {
        return em.createQuery("select p from Payment p", Payment.class)
                .getResultList();
    }
}
