package com.devtalk.member.memberservice.member.application.port.out.dto;

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

    @Data
    @Builder
    public static class UpdateRes {
        private String email;
        private String name;
        private String phoneNumber;
        private List<String> categories;

        public static MyPageRes.UpdateRes of (Member member, List<String> categories) {
            return UpdateRes.builder()
                    .email(member.getEmail())
                    .name(member.getName())
                    .phoneNumber(member.getPhoneNumber())
                    .categories(categories)
                    .build();
        }
    }
}
