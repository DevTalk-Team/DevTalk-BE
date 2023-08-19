package com.devtalk.consultation.consultationservice.integrationtest.setup;

import com.devtalk.consultation.consultationservice.consultation.domain.consultation.Consultation;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.ProcessStatus;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsultationSetUp {

    @Autowired
    private EntityManager em;

//    public Consultation saveConsultation(Consulter consulter) {
//        Consultation consultation = Consultation.createConsultation(consulter);
//        em.persist(consultation);
//        em.flush();
//        return consultation;
//    }


    public Consultation findConsultationByProductId(Long productId, ProcessStatus status) {
        return em.createQuery("select ri from Consultation ri where ri.productId = :productId and ri.status = :status", Consultation.class)
                .setParameter("productId", productId)
                .setParameter("status", status)
                .getSingleResult();
    }
}
