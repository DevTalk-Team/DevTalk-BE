package com.devtalk.member.memberservice.member.adapter.in.web.dto;

import com.devtalk.member.memberservice.member.application.port.in.dto.ConsultantReq;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
        private Integer f2f1h;
        private Integer nf2f1h;

        @Builder.Default
        private MultipartFile profileImage;

        public ConsultantReq.InfoReq toReq() {
            return ConsultantReq.InfoReq.builder()
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

    @Getter
    public static class ListInput {
        private List<String> list;
    }
}

