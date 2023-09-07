package com.devtalk.member.memberservice.member.domain.consultation;

import com.devtalk.member.memberservice.member.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ConsultantInfo {
    @Id @GeneratedValue
    @Column(name = "CONSULTANT_INFO_ID")
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONSULTANT_ID")
    private Member member;
    private int year;
    private String company;
    @Column(name = "SELF_INTRODUCTION")
    private String selfIntroduction;
    private String career;
    private String field;
    private String skill;
    private Integer call15m;
    private Integer call30m;
    private Integer video30m;
    private Integer f2f1h;

    public static ConsultantInfo setMember(Member member) {
        ConsultantInfo consultantInfo = new ConsultantInfo();
        consultantInfo.member = member;
        return consultantInfo;
    }
}
