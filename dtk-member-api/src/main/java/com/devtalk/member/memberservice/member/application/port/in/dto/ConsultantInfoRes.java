package com.devtalk.member.memberservice.member.application.port.in.dto;

import com.devtalk.member.memberservice.member.domain.consultation.ConsultantInfo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConsultantInfoRes {
    private int year;
    private String company;
    private String selfIntroduction;
    private String career;
    private String field;
    private String skill;
    private Integer call15m;
    private Integer call30m;
    private Integer video30m;
    private Integer f2f1h;

    public static ConsultantInfoRes toRes(ConsultantInfo info) {
        return ConsultantInfoRes.builder()
                .year(info.getYear())
                .company(info.getCompany())
                .selfIntroduction(info.getSelfIntroduction())
                .career(info.getCareer())
                .field(info.getField())
                .skill(info.getSkill())
                .call15m(info.getCall15m())
                .call30m(info.getCall30m())
                .video30m(info.getVideo30m())
                .f2f1h(info.getF2f1h())
                .build();
    }
}
