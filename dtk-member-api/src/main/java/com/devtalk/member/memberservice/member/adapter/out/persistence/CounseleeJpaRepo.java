package com.devtalk.member.memberservice.member.adapter.out.persistence;

import com.devtalk.member.memberservice.member.domain.Counselee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CounseleeJpaRepo extends JpaRepository<CounseleeJpaEntity, Long> {
}
