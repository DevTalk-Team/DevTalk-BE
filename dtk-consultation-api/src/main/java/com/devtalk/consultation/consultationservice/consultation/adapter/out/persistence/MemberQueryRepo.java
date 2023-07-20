package com.devtalk.consultation.consultationservice.consultation.adapter.out.persistence;

import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.MemberQueryableRepo;
import com.devtalk.consultation.consultationservice.consultation.domain.member.Consultant;
import com.devtalk.consultation.consultationservice.consultation.domain.member.QConsultant;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.devtalk.consultation.consultationservice.consultation.domain.member.QConsultant.*;

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
}
