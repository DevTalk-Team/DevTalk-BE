package com.devtalk.member.memberservice.member.application.port.out.dto;

import com.devtalk.member.memberservice.member.domain.consultation.ConsultantInfo;
import lombok.*;

public class ConsultantRes {

    @Builder
    @Data // TODO 왜 해결됐을까...?
//    @NoArgsConstructor
//    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class InfoRes {
        private int year;
        private String company;
        private String selfIntroduction;
        private String career;
        private String field;
        private String skill;
        private Integer f2f1h;
        private Integer nf2f1h;

        public static InfoRes of(ConsultantInfo info) {
            return InfoRes.builder()
                    .year(info.getYear())
                    .company(info.getCompany())
                    .selfIntroduction(info.getSelfIntroduction())
                    .career(info.getCareer())
                    .field(info.getField())
                    .skill(info.getSkill())
                    .f2f1h(info.getF2f1h())
                    .nf2f1h(info.getNf2f1h())
                    .build();
        }

        public ConsultantInfo toEntity() {
            return ConsultantInfo.builder()
                    .year(year)
                    .company(company)
                    .selfIntroduction(selfIntroduction)
                    .career(career)
                    .field(field)
                    .skill(skill)
                    .f2f1h(f2f1h)
                    .nf2f1h(nf2f1h)
                    .build();
        }
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ConsultationRes {
        private String email;
        private String name;
        private int year;
        private Integer cost;
    }
}
