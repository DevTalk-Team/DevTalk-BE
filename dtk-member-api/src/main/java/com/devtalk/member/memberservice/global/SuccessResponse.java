package com.devtalk.member.memberservice.global;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SuccessResponse<T> {
    private String code;
    private String message;
    private T result;

    public SuccessResponse(SuccessCode successCode, T result) {
        this.code = successCode.getCode();
        this.message = successCode.getMessage();
        this.result = result;
    }
}
