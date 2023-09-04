package com.devtalk.product.productservice.global.error;

import lombok.Getter;

import static org.springframework.http.HttpStatus.*;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    /**
     * Common
     */
    INVALID_INPUT_VALUE(BAD_REQUEST, "0000", "입력값이 잘못되었습니다."),
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "0001", "서버 내부 에러"),

    /**
     * Consultation
     */



    REGIST_PRODUCT(CONFLICT, "05011", "상품등록에 실패하였습니다."),
    NOT_FOUND_CONSULTATION(NOT_FOUND, "05021", "상품조회에 실패하였습니다.."),
    NOT_FOUND_CONSULTANT(NOT_FOUND, "05031", "상담사 조회를 실패하였습니다."),
    NOT_FOUND_CONSULTER(NOT_FOUND, "05032", "내담자 조회를 실패하였습니다."),


    UPDATE_RESERVATION(CONFLICT, "05041", "상품수정에 실패하였습니다."),
    NOT_FOUND_RESERVED_DETAILS(NOT_FOUND, "05051", "예약 세부 정보를 찾을 수 없습니다."),

    NOT_FOUND_PRODUCT (NOT_FOUND, "05052", "상픔 정보를 찾을 수 없습니다."),

    NOT_FOUND_MEMBER (NOT_FOUND, "05052", "회원 정보를 찾을 수 없습니다."),

    DELETE_RESERVATION(CONFLICT, "05041", "상품조회에실패하였습니다.");







    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus httpStatus, final String code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
