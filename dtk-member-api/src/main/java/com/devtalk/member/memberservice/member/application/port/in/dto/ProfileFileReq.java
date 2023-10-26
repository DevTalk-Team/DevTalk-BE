package com.devtalk.member.memberservice.member.application.port.in.dto;

import com.devtalk.member.memberservice.member.domain.consultation.ConsultantInfo;
import com.devtalk.member.memberservice.member.domain.consultation.ProfileImage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileFileReq {
    private ConsultantInfo consultantInfo;
    private String fileUrl;
    private String originFileName;
    private String storedFileName;

    public ProfileImage toEntity(ConsultantInfo consultantInfo) {
        return ProfileImage.createProfileImage(consultantInfo, fileUrl, originFileName, storedFileName);
    }
}
