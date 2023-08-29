//package com.devtalk.product.productservice.integrationtest.setup;
//
//import com.devtalk.product.productservice.product.domain.member.Consultant;
//import jakarta.persistence.EntityManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ConsultantSetUp {
//
//    @Autowired
//    private EntityManager em;
//
//
//    public Consultant saveConsultant(Long consulatantId, int NF2F, int F2F, String area
//                                     ) {
//        Consultant consultant = Consultant.createConsultant(consulatantId, NF2F, F2F, area);
//        em.persist(consultant);
//        em.flush();
//        return consultant;
//    }
//}