package com.devtalk.member.memberservice.member.application.port.out.repository;

import com.devtalk.member.memberservice.member.domain.consultation.ConsultationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultationTypeRepo extends JpaRepository<ConsultationType, Long> {
    ConsultationType findByConsultationType(String consultationType);
}
