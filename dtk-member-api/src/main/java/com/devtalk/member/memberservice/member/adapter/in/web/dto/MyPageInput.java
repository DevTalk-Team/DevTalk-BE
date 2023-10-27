package com.devtalk.member.memberservice.member.adapter.in.web.dto;

import com.devtalk.member.memberservice.member.application.port.in.dto.MyPageReq;
import lombok.Data;

import java.util.List;

public class MyPageInput {
    @Data
    public static class UpdateInput {
        private String name;
        private String phoneNumber;
        private List<String> categories;

        public MyPageReq.UpdateReq toReq() {
            return MyPageReq.UpdateReq.builder()
                    .name(name)
                    .phoneNumber(phoneNumber)
                    .categories(categories)
                    .build();
        }
    }
}
