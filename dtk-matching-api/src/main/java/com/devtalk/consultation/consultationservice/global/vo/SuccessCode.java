package com.devtalk.consultation.consultationservice.global.vo;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SuccessCode {

    CONSULTATION_RESERVATION_SUCCESS(HttpStatus.CREATED, "0200", "상담 예약 성공"),
    CONSULTATION_CONSULTER_CANCEL_SUCCESS(HttpStatus.OK, "0201", "내담자가 상담 취소 성공"),
    CONSULTATION_CONSULTANT_CANCEL_SUCCESS(HttpStatus.OK, "0202", "상담사가 상담 취소 성공"),
    CONSULTATION_MODIFICATION_SUCCESS(HttpStatus.OK, "0203", "상담 수정 성공"),
    CONSULTER_CONSULTATION_SEARCH_SUCCESS(HttpStatus.OK, "0204", "상담 조회 성공");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    SuccessCode(final HttpStatus httpStatus, final String code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
