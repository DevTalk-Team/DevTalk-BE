package com.devtalk.consultation.consultationservice.consultation.domain.member;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("CONSULTER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Consultant extends Member {

    private String occupation;

    private Integer career;

    private Consultant(Long memberId, String loginId, String name, RoleType roleType, String occupation, Integer career) {
        super(memberId, loginId, name, roleType);
        this.occupation = occupation;
        this.career = career;
    }

    /**
     * 상담 서비스에 전문가가 존재하지 않을 때만 생성함
     */
    public static Consultant createConsulter(Long memberId, String loginId, String name, RoleType roleType,
                                             String occupation, Integer career) {
        return new Consultant(memberId, loginId, name, roleType, occupation, career);
    }


}
