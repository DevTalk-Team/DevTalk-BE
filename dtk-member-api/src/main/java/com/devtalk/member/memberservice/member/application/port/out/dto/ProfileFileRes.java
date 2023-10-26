package com.devtalk.member.memberservice.member.application.port.out.dto;

import com.devtalk.member.memberservice.member.domain.consultation.ProfileImage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileFileRes {
    private String fileUrl;
    private String originFileName;

    public static ProfileFileRes of(ProfileImage profileImage) {
        return ProfileFileRes.builder()
                .fileUrl(profileImage.getFileUrl())
                .originFileName(profileImage.getOriginFileName())
                .build();
    }
}
