package com.devtalk.consultation.consultationservice.consultation.adapter.out.persistence;

import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.ConsultationQueryableRepo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.devtalk.consultation.consultationservice.consultation.domain.consultation.ProcessStatus.*;
import static com.devtalk.consultation.consultationservice.consultation.domain.consultation.QConsultation.*;

@Repository
@RequiredArgsConstructor
public class ConsultationQueryRepo implements ConsultationQueryableRepo {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsByProductIdInReservedItem(Long productId) {
        return queryFactory
                .select(consultation.productId)
                .from(consultation)
                .where(consultation.productId.eq(productId)
                        .and(consultation.status.in(ACCEPTED, PAID)))
                .fetchFirst() != null;
    }
}
