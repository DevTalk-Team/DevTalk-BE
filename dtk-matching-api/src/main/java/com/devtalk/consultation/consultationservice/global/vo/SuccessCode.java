package com.devtalk.consultation.consultationservice.global.vo;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SuccessCode {

    CONSULTATION_RESERVATION_SUCCESS(HttpStatus.CREATED, "0200", "상담 예약 성공"),
    CONSULTATION_CONSULTER_CANCEL_SUCCESS(HttpStatus.OK, "0201", "내담자가 상담 취소 성공"),
    CONSULTATION_CONSULTANT_CANCEL_SUCCESS(HttpStatus.OK, "0202", "상담사가 상담 취소 성공"),
    CONSULTATION_MODIFICATION_SUCCESS(HttpStatus.OK, "0203", "상담 수정 성공"),
    CONSULTER_CONSULTATION_SEARCH_SUCCESS(HttpStatus.OK, "0204", "내담자 상담 조회 성공"),
    CONSULTER_CONSULTATION_DETAIL_SEARCH_SUCCESS(HttpStatus.OK, "0205", "내담자 상담 상세 조회 성공"),
    CONSULTER_CANCELED_CONSULTATION_SEARCH_SUCCESS(HttpStatus.OK, "0206", "내담자 취소 사유 조회 성공"),
    CONSULTATION_REVIEW_WRITE_SUCCESS(HttpStatus.CREATED, "0207", "상담 리뷰 작성 성공");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    SuccessCode(final HttpStatus httpStatus, final String code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
