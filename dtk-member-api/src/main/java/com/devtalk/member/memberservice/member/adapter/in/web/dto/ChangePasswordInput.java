package com.devtalk.member.memberservice.member.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangePasswordInput {
    @NotBlank
    private String password;
    @NotBlank
    private String newPassword;
}
