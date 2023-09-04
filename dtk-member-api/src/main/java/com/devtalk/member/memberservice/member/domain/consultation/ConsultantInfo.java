package com.devtalk.member.memberservice.member.domain.consultation;

import com.devtalk.member.memberservice.member.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultantInfo {
    @Id @GeneratedValue
    @Column(name = "CONSULTANT_INFO_ID")
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONSULTANT_ID")
    private Member member;
    private int year;
    private String company;
    @Embedded
    private Introduction introduction;
    @Embedded
    private Price price;

    public static ConsultantInfo setMember(Member member) {
        ConsultantInfo consultantInfo = new ConsultantInfo();
        consultantInfo.member = member;
        return consultantInfo;
    }
}
