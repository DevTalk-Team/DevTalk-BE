package com.devtalk.member.memberservice.member.adapter.out.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class FindProfileOutput {
    @NoArgsConstructor
    @Data
    public static class EmailOutput {
        String email;

        public EmailOutput(String email) {
            this.email = email;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberOutput {
        String name;
        String email;
    }
}
