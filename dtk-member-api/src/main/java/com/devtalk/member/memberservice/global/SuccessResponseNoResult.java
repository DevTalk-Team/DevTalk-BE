package com.devtalk.member.memberservice.global;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SuccessResponseNoResult {

    private String code;
    private String message;

    public SuccessResponseNoResult(SuccessCode conditionCode) {
        this.code = conditionCode.getCode();
        this.message = conditionCode.getMessage();
    }
}
