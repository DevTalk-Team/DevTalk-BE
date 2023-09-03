package com.devtalk.product.productservice.product.adapter.out.web.persistence;

import com.devtalk.product.productservice.product.domain.member.Consultant;
import com.devtalk.product.productservice.product.domain.member.Consulter;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor

public class MemberQueryRepo {
    private final JPAQueryFactory queryFactory;



}
