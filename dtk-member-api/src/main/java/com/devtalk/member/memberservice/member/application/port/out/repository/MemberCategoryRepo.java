package com.devtalk.member.memberservice.member.application.port.out.repository;

import com.devtalk.member.memberservice.member.domain.category.MemberCategory;
import com.devtalk.member.memberservice.member.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberCategoryRepo extends JpaRepository<MemberCategory, Long> {
    List<MemberCategory> findAllByMember(Member member);
}
