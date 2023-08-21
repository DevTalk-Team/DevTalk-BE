package com.devtalk.member.memberservice.member.adapter.out.persistence;

import com.devtalk.member.memberservice.member.application.port.out.repository.MemberQueryableRepo;
import com.devtalk.member.memberservice.member.domain.member.Member;
import com.devtalk.member.memberservice.member.domain.member.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.devtalk.member.memberservice.member.domain.member.QMember.member;

@Repository
public class MemberQueryRepo implements MemberQueryableRepo {

    @Autowired
    EntityManager em;

    private JPAQueryFactory queryFactory;

    @Override
    public String findEmailByNameAndPhoneNumber(String name, String phoneNumber) {
        queryFactory = new JPAQueryFactory(em);

        return queryFactory
                .select(member.email)
                .from(member)
                .where(member.name.eq(name), member.phoneNumber.eq(phoneNumber))
                .fetchOne();
    }
}
