package com.devtalk.member.memberservice.member.application.port.out.dto;

import com.devtalk.member.memberservice.member.domain.member.Member;
import com.devtalk.member.memberservice.member.domain.member.MemberType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class MemberRes {
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class InfoRes {
        private String name;
        private String email;
        private String phoneNumber;

        public static InfoRes of(Member member) {
            return InfoRes.builder()
                    .name(member.getName())
                    .email(member.getEmail())
                    .phoneNumber(member.getPhoneNumber())
                    .build();
        }
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ConsultantRes {
        private Long consultantId;
        private String name;
        private MemberType memberType;
        private Integer f2fCost;
        private Integer nf2fCost;

        public static ConsultantRes of(Member member, Integer f2f1h, Integer nf2f1h) {
            return ConsultantRes.builder()
                    .consultantId(member.getId())
                    .name(member.getName())
                    .memberType(member.getMemberType())
                    .f2fCost(f2f1h)
                    .nf2fCost(nf2f1h)
                    .build();
        }
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ConsulterRes {
        private Long consulterId;
        private String name;
        private MemberType memberType;

        public static ConsulterRes of(Member member) {
            return ConsulterRes.builder()
                    .consulterId(member.getId())
                    .name(member.getName())
                    .memberType(member.getMemberType())
                    .build();
        }
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ProfileRes {
        private Long id;
        private MemberType memberType;
        private String name;
        private String email;
        private String password;
        private String phoneNumber;

        public static ProfileRes of(Member member) {
            return ProfileRes.builder()
                    .id(member.getId())
                    .memberType(member.getMemberType())
                    .name(member.getName())
                    .email(member.getEmail())
                    .password(member.getPassword())
                    .phoneNumber(member.getPhoneNumber())
                    .build();
        }
    }
}