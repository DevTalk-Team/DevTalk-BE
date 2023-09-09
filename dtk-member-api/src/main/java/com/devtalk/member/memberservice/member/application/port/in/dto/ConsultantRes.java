package com.devtalk.member.memberservice.member.application.port.in.dto;

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
        private Integer call15m;
        private Integer call30m;
        private Integer video30m;
        private Integer f2f1h;

        public static InfoRes of(ConsultantInfo info) {
            return InfoRes.builder()
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

    @Builder
    @Data
    public static class TypeRes {

    }

    @Builder
    @Data
    public static class RegionRes {
    }
}
