//package com.devtalk.product.productservice.product.adapter.out.web.persistence;
//
//import com.devtalk.product.productservice.product.application.port.out.repository.MemberQueryableRepo;
//import com.devtalk.product.productservice.product.domain.member.Consultant;
//import com.devtalk.product.productservice.product.domain.member.Consulter;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//import static com.devtalk.product.productservice.product.domain.member.QConsultant.consultant;
//import static com.devtalk.product.productservice.product.domain.member.QConsulter.consulter;
//
//@Repository
//@RequiredArgsConstructor
//public class MemberQueryRepo implements MemberQueryableRepo {
//
//    private final JPAQueryFactory queryFactory;
//
//    @Override
//    public Optional<Consultant> findByConsultantId(Long consultantId) {
//        return Optional.ofNullable(
//                queryFactory.selectFrom(consultant)
//                        .where(consultant.id.eq(consultantId))
//                        .fetchOne()
//        );
//    }
//
//    @Override
//    public Optional<Consulter> findByConsulterId(Long consulterId) {
//        return Optional.empty();
//    }
//
//    @Override
//    public Optional<Consulter> findById(Long memberId) {
//        return Optional.empty();
//    }
//
//    @Override
//    public boolean existsByConsultantId(Long consultantId) {
//        return queryFactory
//                .select(consultant.id)
//                .from(consultant)
//                .where(consultant.id.eq(consultantId))
//                .fetchFirst() != null;
//    }
//
//    @Override
//    public boolean existsByConsulterId(Long consulterId) {
//        return queryFactory
//                .select(consulter.id)
//                .from(consulter)
//                .where(consulter.id.eq(consulterId))
//                .fetchFirst() != null;
//    }
//}
