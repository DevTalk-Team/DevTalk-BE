package com.devtalk.consultation.consultationservice.consultation.application.port.out.repository;

import com.devtalk.consultation.consultationservice.consultation.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepo<T extends Member> extends JpaRepository<T, Long> {
}
