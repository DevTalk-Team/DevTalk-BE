package com.devtalk.member.memberservice.member.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FindEmailInput {
    @NotBlank
    private String name;
    @NotBlank
    private String phoneNumber;
}