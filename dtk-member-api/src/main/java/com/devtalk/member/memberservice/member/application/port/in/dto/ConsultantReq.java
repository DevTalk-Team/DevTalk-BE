package com.devtalk.member.memberservice.member.application.port.in.dto;

import com.devtalk.member.memberservice.member.application.port.out.dto.ProfileFileRes;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
        @Builder.Default
        private List<MultipartFile> profileFileResList = new ArrayList<>();
    }

    @Builder
    @Getter
    public static class CategoryReq {
        private List<String> categories;
    }

    @Builder
    @Getter
    public static class ConsultationReq {
        private String type;        // 상담 분야: 커리어 상담 ...
        private String category;    // 기술 분야: 웹 ...
        private String f2f;         // 대면 여부: 대면 or 비대면, 게시판
        private String region;      // 대면 시 지역
    }
}
