package com.devtalk.member.memberservice.member.application.port.out.repository;

import com.devtalk.member.memberservice.member.domain.consultation.ConsultantConsultationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultantConsultationTypeRepo extends JpaRepository<ConsultantConsultationType, Long> {
    List<ConsultantConsultationType> findAllByMemberId(Long memberId);
    boolean existsByMemberId(Long memberId);
    void deleteAllByMemberId(Long memberId);
}
