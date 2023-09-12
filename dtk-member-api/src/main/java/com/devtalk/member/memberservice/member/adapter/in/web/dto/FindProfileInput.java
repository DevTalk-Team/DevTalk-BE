package com.devtalk.member.memberservice.member.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class FindProfileInput {
    @Data
    public static class EmailInput {
        @NotBlank
        private String name;
        @NotBlank
        private String phoneNumber;
    }

    @Data
    public static class SendPasswordInput {
        @NotBlank
        private String name;
        @NotBlank
        private String email;
    }

    @Data
    public static class ChangePasswordInput {
        @NotBlank
        private String password;
        @NotBlank
        private String newPassword;
    }
}
