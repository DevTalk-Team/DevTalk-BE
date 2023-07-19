package com.devtalk.consultation.consultationservice.consultation.adapter.out.persistence;

import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.ConsultationQueryableRepo;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.Consultation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ConsultationQueryRepo implements ConsultationQueryableRepo {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Consultation> findByConsulterId(Long consulterId) {
        return queryFactory;
    }
}
