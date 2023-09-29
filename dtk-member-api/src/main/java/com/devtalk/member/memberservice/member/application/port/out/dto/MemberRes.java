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
    public static class ConsultantRes {
        private Long consultantId;
        private String name;
        private MemberType memberType;
        //가격은 상품에서도 쓰기 위함
        private Integer F2F_Cost;
        private Integer NF2F_Cost;

        public static ConsultantRes of(Member member, Integer f2f1h, Integer nf2f1h) {
            return ConsultantRes.builder()
                    .consultantId(member.getId())
                    .name(member.getName())
                    .memberType(member.getMemberType())
                    .F2F_Cost(f2f1h)
                    .NF2F_Cost(nf2f1h)
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
}