package com.devtalk.member.memberservice.member.adapter.in.web.dto;

import com.devtalk.member.memberservice.member.application.port.in.dto.ConsultantReq;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

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

//        @Builder.Default
//        private MultipartFile profileImage;

        public ConsultantReq.InfoReq toReq(List<MultipartFile> files) {
            return ConsultantReq.InfoReq.builder()
                    .year(year)
                    .company(company)
                    .selfIntroduction(selfIntroduction)
                    .career(career)
                    .field(field)
                    .skill(skill)
                    .f2f1h(f2f1h)
                    .nf2f1h(nf2f1h)
                    .profileFileResList(files)
                    .build();
        }

    }

    @Getter
    public static class ListInput {
        private List<String> list;
    }

    @Getter
    public static class ConsultationInput {
        private String type;        // 상담 분야: 커리어 상담 ...
        private String category;    // 기술 분야: 웹 ...
        private String f2f;         // 대면 여부: 대면 or 비대면, 게시판
        private String region;      // 대면 시 지역

        public ConsultantReq.ConsultationReq toReq() {
            return ConsultantReq.ConsultationReq.builder()
                    .type(type)
                    .category(category)
                    .f2f(f2f)
                    .region(region)
                    .build();
        }
    }
}

