package com.devtalk.member.memberservice.member.application.port.in.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class ConsultantReq {

    @Builder
    @Getter
    public static class InfoReq {
        private int year;
        private String company;
        private String selfIntroduction;
        private String career;
        private String field;
        private String skill;
        private Integer f2f1h;
        private Integer nf2f1h;
    }

    @Builder
    @Getter
    public static class CategoryReq {
        private List<String> categories;
    }
}
