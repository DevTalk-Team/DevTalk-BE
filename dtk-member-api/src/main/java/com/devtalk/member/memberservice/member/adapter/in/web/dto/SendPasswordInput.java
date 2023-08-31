package com.devtalk.member.memberservice.member.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SendPasswordInput {
    @NotBlank
    private String name;
    @NotBlank
    private String email;
}
