package com.devtalk.consultation.consultationservice.consultation.adapter.out.persistence;

import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.MemberQueryableRepo;
import com.devtalk.consultation.consultationservice.consultation.domain.member.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.devtalk.consultation.consultationservice.consultation.domain.member.QConsultant.*;
import static com.devtalk.consultation.consultationservice.consultation.domain.member.QConsulter.*;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepo implements MemberQueryableRepo {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Consultant> findByConsultantId(Long consultantId) {
        return Optional.ofNullable(
                queryFactory.selectFrom(consultant)
                        .where(consultant.id.eq(consultantId))
                        .fetchOne()
        );
    }

    @Override
    public Optional<Consulter> findByConsulterId(Long consulterId) {
        return Optional.empty();
    }
    @Override
    public Optional<Member> findByMemberId(Long memberId) {
        return Optional.empty();
    }

    @Override
    public boolean existsByConsultantId(Long consultantId) {
        return queryFactory
                .select(consultant.id)
                .from(consultant)
                .where(consultant.id.eq(consultantId))
                .fetchFirst() != null;
    }

    @Override
    public boolean existsByConsulterId(Long consulterId) {
        return queryFactory
                .select(consulter.id)
                .from(consulter)
                .where(consulter.id.eq(consulterId))
                .fetchFirst() != null;
    }
}
