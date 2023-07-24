package com.devtalk.member.memberservice.member.adapter.out.persistence;

import com.devtalk.member.memberservice.member.application.port.out.repository.SignUpCommandableRepo;
import com.devtalk.member.memberservice.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 입력값을 받고
 * 입력값을 데이터베이스용 형식으로 매핑한 후 데이터 베이스로 보낸 다음
 * JPA를 사용하는 경우 JPA 엔티티 도메인 형식으로 매핑
 * MongoDB를 사용하는 경우 MongoDB 도큐먼트 형식으로 매핑
 * 데이터베이스 출력값을 애플리케이션 형식으로 매핑하여
 * 출력값을 반환합니다.
 */

@Repository
@RequiredArgsConstructor
public class SignUpCommandRepo implements SignUpCommandableRepo {

    private final MemberJpaRepo repository;

    @Override
    public void save(Member member) {
        repository.save(member);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
