package com.devtalk.member.memberservice.member.adapter.out.persistence;

import com.devtalk.member.memberservice.member.application.port.out.repository.ProfileImageQueryableRepo;
import com.devtalk.member.memberservice.member.domain.consultation.ProfileImage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.devtalk.member.memberservice.member.domain.consultation.QProfileImage.profileImage;

@Repository
@RequiredArgsConstructor
public class ProfileImageQueryRepo implements ProfileImageQueryableRepo {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ProfileImage> findByConsultantInfoId(Long consultantInfoId) {
        return jpaQueryFactory
                .selectFrom(profileImage)
                .where(profileImage.consultantInfo.id.eq(consultantInfoId))
                .fetch();
    }

    @Override
    public long deleteAllByConsultantInfoId(Long consultantInfoId) {
        return jpaQueryFactory
                .delete(profileImage)
                .where(profileImage.consultantInfo.id.eq(consultantInfoId))
                .execute();
    }
}
