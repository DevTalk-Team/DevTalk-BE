package com.devtalk.consultation.consultationservice.consultation.application.port.out.repository;

import com.devtalk.consultation.consultationservice.consultation.domain.member.Consultant;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberQueryableRepo {
    Optional<Consultant> findByConsultantId(Long consultantId);
}
