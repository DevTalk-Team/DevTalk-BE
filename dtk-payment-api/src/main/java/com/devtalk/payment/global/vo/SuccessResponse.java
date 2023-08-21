package com.devtalk.payment.global.vo;

import com.devtalk.payment.global.code.SuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@Getter
public class SuccessResponse<T> {

    private String code;
    private String message;
    private T result;

    public static ResponseEntity<SuccessResponse> toResponseEntity(SuccessCode successCode, Object result) {
        SuccessResponse res = new SuccessResponse(successCode.getCode(), successCode.getMessage(), result);
        return new ResponseEntity<>(res, successCode.getHttpStatus());
    }
}