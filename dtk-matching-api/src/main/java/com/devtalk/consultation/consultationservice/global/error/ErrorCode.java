package com.devtalk.consultation.consultationservice.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

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
    INVALID_RESERVATION_REQUEST(CONFLICT, "02001", "잘못된 예약 요청입니다."),
    EXCESS_FILE_LIST_COUNT(CONFLICT, "02002", "최대 허용 파일 개수를 초과하였습니다."),
    EXCESS_FILE_LIST_SIZE(CONFLICT, "02003", "파일 리스트의 최대 허용 사이즈를를 초과하였습니다."),
    UNSUPPORTED_FILE_EXTENSION(CONFLICT, "02004", "허용되지 않는 확장자를 가진 파일이 입력되었습니다."),
    NOT_FOUND_CONSULTER(NOT_FOUND, "02005", "등록되지 않은 내담자의 요청입니다."),
    NOT_FOUND_CONSULTANT(NOT_FOUND, "02006", "해당 전문가가 등록되어 있지 않습니다."),
    DUPLICATED_RESERVATION(CONFLICT, "02007", "이미 예약된 상담입니다."),
    IRREVOCABLE_STATUS(CONFLICT, "02008", "취소할 수 없는 매칭상태입니다."),
    NOT_FOUND_CONSULTATION(NOT_FOUND, "02031", "존재하지 않는 요청입니다."),
    UNAUTHORIZED_CONSULTATION(UNAUTHORIZED, "02051", "권한이 없는 요청입니다.");





    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus httpStatus, final String code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

}
