package com.devtalk.consultation.consultationservice.consultation.domain.member;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("CONSULTER")
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Consultant extends Member {

    private String occupation;

    private Integer career;

    /**
     * 상담 서비스에 전문가가 존재하지 않을 때만 생성함
     */
    public static Consultant createConsulter(Long memberId, String loginId, String name, RoleType roleType,
                                             String occupation, Integer career) {
        return Consultant.builder()
                .id(memberId)
                .loginId(loginId)
                .name(name)
                .role(roleType)
                .occupation(occupation)
                .career(career)
                .build();
    }


}
