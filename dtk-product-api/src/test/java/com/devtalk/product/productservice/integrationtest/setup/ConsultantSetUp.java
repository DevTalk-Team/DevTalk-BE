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
//    public Consultant saveConsultant(Long memberId, String loginId, String name,
//                                     ) {
//        Consultant consultant = Consultant.createConsultant(memberId, loginId, name);
//        em.persist(consultant);
//        em.flush();
//        return consultant;
//    }
//}