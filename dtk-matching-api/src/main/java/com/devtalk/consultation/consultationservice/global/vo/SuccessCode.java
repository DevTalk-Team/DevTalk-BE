package com.devtalk.consultation.consultationservice.global.vo;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SuccessCode {

    RESERVATION_SUCCESS(HttpStatus.CREATED, "0200", "상담 예약 성공");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    SuccessCode(final HttpStatus httpStatus, final String code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
