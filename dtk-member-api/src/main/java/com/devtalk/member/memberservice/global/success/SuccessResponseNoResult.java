package com.devtalk.member.memberservice.global.success;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponseNoResult {

    private String code;
    private String message;

    public SuccessResponseNoResult(SuccessCode conditionCode) {
        this.code = conditionCode.getCode();
        this.message = conditionCode.getMessage();
    }

    public static ResponseEntity<SuccessResponseNoResult> toResponseEntity(SuccessCode successCode) {
        SuccessResponseNoResult res = new SuccessResponseNoResult(successCode.getCode(), successCode.getMessage());
        return new ResponseEntity<>(res, successCode.getHttpStatus());
    }
}
