package com.devtalk.member.memberservice.member.application.port.in.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

public class MyPageReq {
    @Data
    @Builder
    public static class UpdateReq {
        private String name;
        private String phoneNumber;
        private List<String> categories;
    }
}
