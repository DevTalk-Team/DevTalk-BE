package com.devtalk.product.productservice.product.adapter.out.web.persistence;

import com.devtalk.product.productservice.product.domain.member.Consultant;
import com.devtalk.product.productservice.product.domain.member.Consulter;
import com.devtalk.product.productservice.product.domain.member.Member;
import com.devtalk.product.productservice.product.domain.product.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface MemberQueryRepo extends JpaRepository<Member, Long> {
    Optional<Consultant> findConsultantById(Long id);
    Optional<Consulter> findConsulterById(Long id);
    Optional<Product> findByConsultantIdAndReservationAt(Long consultantId, LocalDateTime reservationAt);
}
