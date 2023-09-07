package com.devtalk.member.memberservice.member.domain.consultation;

import com.devtalk.member.memberservice.member.application.port.in.dto.ConsultantReq;
import com.devtalk.member.memberservice.member.application.port.in.dto.ConsultantRes;
import com.devtalk.member.memberservice.member.domain.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

//    @JsonIgnore
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

    public ConsultantInfo update(ConsultantReq.InfoReq req) {
        this.year = req.getYear();
        this.company = req.getCompany();
        this.selfIntroduction = req.getSelfIntroduction();
        this.career = req.getCareer();
        this.field = req.getField();
        this.skill = req.getSkill();
        this.call15m = req.getCall15m();
        this.call30m = req.getCall30m();
        this.video30m = req.getVideo30m();
        this.f2f1h = req.getF2f1h();
        return this;
    }
}
