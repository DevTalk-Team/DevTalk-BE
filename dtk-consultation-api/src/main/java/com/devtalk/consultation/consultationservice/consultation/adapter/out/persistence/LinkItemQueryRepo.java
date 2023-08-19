package com.devtalk.consultation.consultationservice.consultation.adapter.out.persistence;

import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.LinkItemQueryableRepo;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.QReservedItem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.devtalk.consultation.consultationservice.consultation.domain.consultation.QReservedItem.*;

@Repository
@RequiredArgsConstructor
public class LinkItemQueryRepo implements LinkItemQueryableRepo {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsByProductIdInReservedItem(Long productId) {
        return queryFactory
                .select(reservedItem.id)
                .from(reservedItem)
                .where(reservedItem.productId.eq(productId))
                .fetchFirst() != null;
    }
}
