package com.devtalk.payment.global.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum SuccessCode {
    GET_PAYMENT_INFO_SUCCESS(OK, "0301", "결제 정보 조회 성공"),

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private String message;

    SuccessCode(final HttpStatus httpStatus, final String code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
