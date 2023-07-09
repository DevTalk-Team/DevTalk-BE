package com.devtalk.consultation.consultationservice.consultation.domain.member;

import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("CONSULTER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Consulter extends Member {
    private Consulter(Long memberId, String loginId, String name, RoleType roleType) {
        super(memberId, loginId, name, roleType);
    }

    /**
     * 상담 서비스에 내담자가 존재하지 않을 때만 생성함
     */
    public static Consulter createConsulter(Long memberId, String loginId, String name, RoleType roleType) {
        return new Consulter(memberId, loginId, name, roleType);
    }
}
