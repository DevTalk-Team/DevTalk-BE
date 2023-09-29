package com.devtalk.common.commonservice.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {

    /* 공통 */
    INVALID_INPUT_VALUE(BAD_REQUEST, "00000", "입력 값이 잘못되었습니다."),
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "00001", "서버 내부 에러"),
    /* AUTH */
    AUTH_CODE_MISMATCHING(CONFLICT, "01141", "인증번호가 일치하지 않습니다."),
    MEMBER_NOT_FOUND(CONFLICT, "01151", "일치하는 회원 정보가 없습니다."),
    WRONG_PASSWORD(CONFLICT, "01152", "비밀번호를 잘못 입력하였습니다."),
    AUTH_FAIL(UNAUTHORIZED, "01153", "인증 실패"),
    /* TOKEN */
    INCORRECT_SIGNATURE(BAD_REQUEST, "01154", "잘못된 서명입니다."),
    EXPIRED_TOKEN(BAD_REQUEST, "01155", "만료된 토큰"),
    UNSUPPORTED_TOKEN(BAD_REQUEST, "01156", "지원되지 않는 토큰"),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus httpStatus, final String code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
