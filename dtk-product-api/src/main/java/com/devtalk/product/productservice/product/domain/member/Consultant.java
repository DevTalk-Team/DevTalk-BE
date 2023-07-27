package com.devtalk.product.productservice.product.domain.member;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
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

//카테고리 3개 테이블 분리
//datetime localdatetime list r