package com.devtalk.consultation.consultationservice.integrationtest.setup;

import com.devtalk.consultation.consultationservice.consultation.domain.consultation.Consultation;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.ReservedItem;
import com.devtalk.consultation.consultationservice.consultation.domain.member.Consulter;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LinkItemSetUp {

    @Autowired
    private EntityManager em;

    public Consultation saveConsultation(Consulter consulter) {
        Consultation consultation = Consultation.createConsultation(consulter);
        em.persist(consultation);
        em.flush();
        return consultation;
    }


    public ReservedItem findReservedItemByProductId(Long productId) {
        return em.createQuery("select ri from ReservedItem ri where ri.productId = :productId", ReservedItem.class)
                .setParameter("productId", productId)
                .getSingleResult();
    }
}
