package com.devtalk.member.memberservice.member.domain.consultation;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProfileImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROFILE_IMAGE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONSULTANT_INFO_ID")
    private ConsultantInfo consultantInfo;

    private String fileUrl;

    private String originFileName;

    private String storedFileName;

    public static ProfileImage createProfileImage(ConsultantInfo consultantInfo, String fileUrl, String originFileName, String storedFileName) {
        return ProfileImage.builder()
                .consultantInfo(consultantInfo)
                .fileUrl(fileUrl)
                .originFileName(originFileName)
                .storedFileName(storedFileName)
                .build();
    }
}
