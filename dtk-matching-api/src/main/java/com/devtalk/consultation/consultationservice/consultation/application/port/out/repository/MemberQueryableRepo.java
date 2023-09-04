package com.devtalk.consultation.consultationservice.consultation.application.port.out.repository;

import com.devtalk.consultation.consultationservice.consultation.domain.member.Consultant;
import com.devtalk.consultation.consultationservice.consultation.domain.member.Consulter;
import com.devtalk.consultation.consultationservice.consultation.domain.member.Member;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface MemberQueryableRepo {
    Optional<Consultant> findByConsultantId(Long consultantId);

    Optional<Consulter> findByConsulterId(Long consulterId);
    Optional<Member> findByMemberId(Long memberId);


    boolean existsByConsultantId(Long consultantId);

    boolean existsByConsulterId(Long consulterId);
}
