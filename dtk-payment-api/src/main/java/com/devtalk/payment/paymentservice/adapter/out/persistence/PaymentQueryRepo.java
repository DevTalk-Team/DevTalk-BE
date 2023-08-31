package com.devtalk.payment.paymentservice.adapter.out.persistence;

import com.devtalk.payment.paymentservice.application.port.out.repository.PaymentQueryableRepo;
import com.devtalk.payment.paymentservice.domain.payment.Payment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.devtalk.payment.paymentservice.domain.payment.QPayment.payment;

@Repository
@RequiredArgsConstructor
class PaymentQueryRepo implements PaymentQueryableRepo {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Payment> findByConsultationId(Long consultationId) {
        return Optional.ofNullable(
                queryFactory.selectFrom(payment)
                        .where(payment.consultation.id.eq(consultationId))
                        .fetchOne()
        );
    }

    @Override
    public Optional<Payment> findByMerchantId(String merchantId) {
        return Optional.ofNullable(
                queryFactory.selectFrom(payment)
                        .where(payment.merchantId.eq(merchantId))
                        .fetchOne()
        );
    }
}
