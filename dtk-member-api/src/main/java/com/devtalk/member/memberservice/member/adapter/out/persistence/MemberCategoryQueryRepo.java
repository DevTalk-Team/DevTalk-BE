package com.devtalk.member.memberservice.member.adapter.out.persistence;

import com.devtalk.member.memberservice.member.application.port.out.repository.MemberCategoryQueryableRepo;
import com.devtalk.member.memberservice.member.domain.category.MemberCategory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.devtalk.member.memberservice.member.domain.category.QMemberCategory.memberCategory;
import static com.devtalk.member.memberservice.member.domain.member.QMember.member;

@Repository
public class MemberCategoryQueryRepo implements MemberCategoryQueryableRepo {
    @Autowired
    EntityManager em;

    private JPAQueryFactory queryFactory;

    @Override
    public List<MemberCategory> findAllById(Long id) {
        queryFactory = new JPAQueryFactory(em);

        return queryFactory
                .selectFrom(memberCategory)
                .where(memberCategory.member.id.eq(id))
                .fetch();
    }

    @Override
    public void deleteAllById(Long id) {
        queryFactory = new JPAQueryFactory(em);

        queryFactory
                .delete(memberCategory)
                .where(memberCategory.member.id.eq(id))
                .execute();
    }
}
