package com.devtalk.product.productservice.global.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.OK;

@Getter
public enum SuccessCode {
    SUCCESS_REGIST_PRODUCT(HttpStatus.CREATED, "0501", "상품 등록 성공"),
    SUCCESS_SEARCH_CONSULTANT_TABLE(OK, "0502", "상담자 예약 가능 상품 조회 성공"),
    SUCCESS_DELETE_CONSULTATION(OK, "0503", "예약 상품 삭제 성공"),
    SUCCESS_UPDATE_PRODUCT(OK, "0504", "상품 수정 성공"),
    SUCCESS_SEARCH_MY_CONSULTATION(OK, "0505", "마이페이지 예약 리스트 조회 성공"),
    SUCCESS_RESERVE_CONSULTATION(OK, "0506", "상품 예약 성공"),

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