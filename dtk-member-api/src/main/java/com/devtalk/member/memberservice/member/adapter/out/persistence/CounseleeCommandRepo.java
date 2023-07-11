package com.devtalk.member.memberservice.member.adapter.out.persistence;

import com.devtalk.member.memberservice.member.application.port.out.repository.CounseleeCommandableRepo;
import com.devtalk.member.memberservice.member.domain.Counselee;
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
public class CounseleeCommandRepo implements CounseleeCommandableRepo {

    private final CounseleeJpaRepo repository;

    @Override
    public void save(Counselee domain) {
        CounseleeJpaEntity jpaEntity = new CounseleeJpaEntity(domain.getEmail(), domain.getPassword(), domain.getPhoneNumber(), domain.getBirthDate());
        repository.save(jpaEntity);
    }
}
