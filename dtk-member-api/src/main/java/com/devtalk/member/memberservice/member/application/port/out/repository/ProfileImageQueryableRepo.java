package com.devtalk.member.memberservice.member.application.port.out.repository;

import com.devtalk.member.memberservice.member.domain.consultation.ProfileImage;

import java.util.List;

public interface ProfileImageQueryableRepo {
    List<ProfileImage> findByConsultantInfoId(Long consultantInfoId);
    long deleteAllByConsultantInfoId(Long consultantInfoId);
}
