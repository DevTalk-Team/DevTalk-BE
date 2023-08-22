package com.devtalk.member.memberservice.member.application.port.out.repository;

import com.devtalk.member.memberservice.member.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepo extends JpaRepository<Member, Long>, MemberQueryableRepo {
    boolean existsByEmail(String email);
    boolean existsByNameAndPhoneNumber(String name, String phoneNumber);
    Optional<Member> findByEmail(String email);
}
