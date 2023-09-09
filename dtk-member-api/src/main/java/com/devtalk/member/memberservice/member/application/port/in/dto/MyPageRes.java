package com.devtalk.member.memberservice.member.application.port.in.dto;

import com.devtalk.member.memberservice.member.domain.member.Member;
import lombok.Builder;
import lombok.Data;

import java.util.List;

public class MyPageRes {

    @Data
    @Builder
    public static class InfoRes {
        private String name;
        private String phoneNumber;
        private String email;
        private List<String> categories;

        public static MyPageRes.InfoRes of(Member member, List<String> categories) {
            return InfoRes.builder()
                    .name(member.getName())
                    .phoneNumber(member.getPhoneNumber())
                    .email(member.getEmail())
                    .categories(categories)
                    .build();
        }
    }
}
