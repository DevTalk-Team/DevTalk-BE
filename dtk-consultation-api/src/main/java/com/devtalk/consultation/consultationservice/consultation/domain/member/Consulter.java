package com.devtalk.consultation.consultationservice.consultation.domain.member;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("CONSULTER")
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Consulter extends Member {

    /**
     * 상담 서비스에 내담자가 존재하지 않을 때만 생성함
     */
    public static Consulter createConsulter(Long memberId, String loginId, String name, RoleType roleType) {
        return Consulter.builder()
                .id(memberId)
                .loginId(loginId)
                .name(name)
                .role(roleType)
                .build();
    }
}
