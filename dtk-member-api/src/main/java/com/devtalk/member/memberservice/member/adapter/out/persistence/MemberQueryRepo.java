package com.devtalk.member.memberservice.member.adapter.out.persistence;

import com.devtalk.member.memberservice.member.adapter.out.web.dto.FindProfileOutput;
import com.devtalk.member.memberservice.member.application.port.out.dto.ConsultantRes;
import com.devtalk.member.memberservice.member.application.port.out.repository.MemberQueryableRepo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.devtalk.member.memberservice.member.domain.category.QMemberCategory.memberCategory;
import static com.devtalk.member.memberservice.member.domain.consultation.QConsultantConsultationType.consultantConsultationType;
import static com.devtalk.member.memberservice.member.domain.consultation.QConsultantInfo.consultantInfo;
import static com.devtalk.member.memberservice.member.domain.member.QMember.member;
import static com.devtalk.member.memberservice.member.domain.region.QMemberRegion.memberRegion;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberQueryRepo implements MemberQueryableRepo {
    private final JPAQueryFactory queryFactory;

    @Override
    public String findEmailByNameAndPhoneNumber(String name, String phoneNumber) {
        return queryFactory
                .select(member.email)
                .from(member)
                .where(member.name.eq(name), member.phoneNumber.eq(phoneNumber))
                .fetchOne();
    }

    @Override
    public FindProfileOutput.MemberOutput findNameAndEmailById(Long id) {
        return queryFactory
                .select(Projections.constructor(FindProfileOutput.MemberOutput.class,
                        member.name, member.email))
                .from(member)
                .where(member.id.eq(id))
                .fetchOne();
    }

    @Override
    public List<ConsultantRes.ConsultationRes> findNf2fConsultant(Long typeId, Long categoryId) {
        return queryFactory
                .select(Projections.constructor(ConsultantRes.ConsultationRes.class,
                        member.id, member.name, consultantInfo.company, consultantInfo.year, consultantInfo.nf2f1h)).distinct()
                .from(member)
                .join(consultantInfo).on(member.id.eq(consultantInfo.member.id))
                .join(consultantConsultationType)
                .on(consultantConsultationType.member.id.eq(member.id)
                        .and(consultantConsultationType.consultationType.id.eq(typeId)))
                .join(memberCategory)
                .on(memberCategory.member.id.eq(member.id)
                        .and(memberCategory.category.id.eq(categoryId)))
                .fetch();
    }

    @Override
    public List<ConsultantRes.ConsultationRes> findF2fConsultant(Long typeId, Long categoryId, Long regionId) {
        return queryFactory
                .select(Projections.constructor(ConsultantRes.ConsultationRes.class,
                        member.id, member.name, consultantInfo.company, consultantInfo.year, consultantInfo.f2f1h)).distinct()
                .from(member)
                .join(consultantInfo).on(member.id.eq(consultantInfo.member.id))
                .join(consultantConsultationType)
                .on(consultantConsultationType.member.id.eq(member.id)
                        .and(consultantConsultationType.consultationType.id.eq(typeId)))
                .join(memberCategory)
                .on(memberCategory.member.id.eq(member.id)
                        .and(memberCategory.category.id.eq(categoryId)))
                .join(memberRegion)
                .on(memberRegion.member.id.eq(member.id)
                        .and(memberRegion.region.id.eq(regionId)))
                .fetch();
    }
}
