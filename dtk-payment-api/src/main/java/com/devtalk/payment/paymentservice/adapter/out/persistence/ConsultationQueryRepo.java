package com.devtalk.payment.paymentservice.adapter.out.persistence;

import com.devtalk.payment.paymentservice.application.port.out.repository.ConsultationQueryableRepo;
import com.devtalk.payment.paymentservice.domain.consultation.Consultation;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class ConsultationQueryRepo implements ConsultationQueryableRepo {

    final private EntityManager em;

    @Override
    public Consultation findByConsultationId(String consultationId) {
        return em.createQuery("select c from Consultation c " +
                        "where c.consultationId = :consultationId", Consultation.class)
                .setParameter("consultationId", consultationId)
                .getSingleResult();
    }
}
