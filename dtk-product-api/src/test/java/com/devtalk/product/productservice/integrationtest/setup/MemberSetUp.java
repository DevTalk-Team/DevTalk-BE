package com.devtalk.product.productservice.integrationtest.setup;

import com.devtalk.product.productservice.product.domain.member.Consultant;
import com.devtalk.product.productservice.product.domain.member.Consulter;
import com.devtalk.product.productservice.product.domain.member.RoleType;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberSetUp {

    @Autowired
    private EntityManager em;

    public Consulter saveConsulter(Long memberId, String loginId, String name, RoleType roleType) {
        Consulter consulter = Consulter.createConsulter(memberId, loginId, name, roleType);
        em.persist(consulter);
        em.flush();
        return consulter;
    }

    public Consultant saveConsultant(Long memberId, String loginId, String name, RoleType roleType,
                                     String occupation, Integer career) {
        Consultant consultant = Consultant.createConsulter(memberId, loginId, name, roleType, occupation, career);
        em.persist(consultant);
        em.flush();
        return consultant;
    }
}