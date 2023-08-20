package com.devtalk.member.memberservice.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {

    INVALID_INPUT_VALUE(BAD_REQUEST, "0000", "입력 값이 잘못되었습니다."),
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "0001", "서버 내부 에러"),

    /* 멘티 회원가입 */
    DUPLICATED_EMAIL(CONFLICT, "01111", "이미 가입된 이메일입니다."),
    INVALID_VALUE_EMAIL(BAD_REQUEST, "01112", "이메일 형식이 아닙니다."),
    PASSWORD_MISMATCHING(CONFLICT, "01113", "비밀번호가 일치하지 않습니다."),

    /* 전문가 회원가입 */
    CONSULTANT_DUPLICATED_EMAIL(CONFLICT, "01131", "이미 가입된 이메일입니다."),
    CONSULTANT_INVALID_VALUE_EMAIL(BAD_REQUEST, "01132", "이메일 형식이 아닙니다."),
    CONSULTANT_PASSWORD_MISMATCHING(CONFLICT, "01133", "비밀번호가 일치하지 않습니다."),

    AUTH_CODE_MISMATCHING(CONFLICT, "01134", "인증번호가 일치하지 않습니다."),
    MEMBER_NOT_FOUND(CONFLICT, "01141", "일치하는 회원 정보가 없습니다."),
    WRONG_PASSWORD(CONFLICT, "01142", "비밀번호를 잘못 입력하였습니다."),
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
