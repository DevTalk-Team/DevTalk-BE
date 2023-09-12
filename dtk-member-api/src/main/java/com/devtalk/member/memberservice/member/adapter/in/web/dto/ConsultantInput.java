package com.devtalk.member.memberservice.member.adapter.in.web.dto;

import com.devtalk.member.memberservice.member.application.port.in.dto.ConsultantReq;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class ConsultantInput {

    @Getter
    @AllArgsConstructor
    public static class InfoInput {
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

        public ConsultantReq.InfoReq toReq() {
            return ConsultantReq.InfoReq.builder()
                    .year(year)
                    .company(company)
                    .selfIntroduction(selfIntroduction)
                    .career(career)
                    .field(field)
                    .skill(skill)
                    .call15m(call15m)
                    .call30m(call30m)
                    .video30m(video30m)
                    .f2f1h(f2f1h)
                    .build();
        }

    }

    @Getter
    public static class ListInput {
        private List<String> list;
    }
}
