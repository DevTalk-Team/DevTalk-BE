package com.devtalk.member.memberservice.member.application.port.out.repository;

import com.devtalk.member.memberservice.member.domain.region.MemberRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRegionRepo extends JpaRepository<MemberRegion, Long> {
    boolean existsByMemberId(Long memberId);
    void deleteAllByMemberId(Long memberId);

    List<MemberRegion> findAllByMemberId(Long memberId);
}
