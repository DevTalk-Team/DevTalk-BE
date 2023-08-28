package com.devtalk.consultation.consultationservice.global.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@Getter
public class SuccessResponseWithoutResult {
    private String code;
    private String message;

    public static ResponseEntity<SuccessResponseWithoutResult> toResponseEntity(SuccessCode successCode) {
        SuccessResponseWithoutResult res = new SuccessResponseWithoutResult(successCode.getCode(), successCode.getMessage());
        return new ResponseEntity<>(res, successCode.getHttpStatus());
    }
}
