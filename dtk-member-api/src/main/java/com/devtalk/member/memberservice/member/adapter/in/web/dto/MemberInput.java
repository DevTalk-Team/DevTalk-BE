package com.devtalk.member.memberservice.member.adapter.in.web.dto;

import com.devtalk.member.memberservice.member.application.port.in.dto.MemberReq;
import com.devtalk.member.memberservice.member.domain.member.MemberType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class MemberInput {
    @Getter
    @AllArgsConstructor
    public static class SignUpInput {
        @NotBlank(message = "이름은 필수입니다.")
        private final String name;
        @NotBlank(message = "아이디는 필수입니다.")
        @Email(message = "이메일 형식이 아닙니다.")
        private final String email;
        @NotBlank(message = "비밀번호는 필수입니다.")
//    @Pattern(message = "최소 8자 이상이어야 하고, 최소 하나의 문자, 하나의 숫자 및 하나의 특수 문자가 포함되어야 합니다.",
//            regexp = "^(?=.*[a-zA-Z])(?=.*[\\\\W_])(?=.*\\\\d)(?!.*\\\\s).{8,}$")
        private final String password;
        @NotBlank(message = "비밀번호 확인은 필수입니다.")
        private final String checkPassword;
        @NotBlank(message = "휴대폰 번호는 필수입니다.")
        private final String phoneNumber;
        private List<String> category;

        public MemberReq.SignUpReq toReq(MemberType memberType) {
            return MemberReq.SignUpReq.builder()
                    .memberType(memberType)
                    .email(email)
                    .password(password)
                    .checkPassword(checkPassword)
                    .name(name)
                    .phoneNumber(phoneNumber)
                    .category(category)
                    .build();
        }
    }
}
