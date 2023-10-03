package com.devtalk.board.consultationboardservice.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {
    NOT_FOUND_POST(NOT_FOUND, "04001", "해당 게시글 찾기 실패"),
    NOT_FOUND_COMMENT(NOT_FOUND, "04002", "해당 댓글 찾기 실패"),
    EXCESS_FILE_LIST_COUNT(CONFLICT, "04003", "최대 허용 파일 개수를 초과하였습니다."),
    EXCESS_FILE_LIST_SIZE(CONFLICT, "04004", "파일 리스트의 최대 허용 사이즈를를 초과하였습니다."),
    UNSUPPORTED_FILE_EXTENSION(CONFLICT, "04005", "허용되지 않는 확장자를 가진 파일이 입력되었습니다."),
    NOT_FOUND_FILE(NOT_FOUND, "04006", "파일을 찾을 수 없습니다."),
    INCORRECT_USER_TRY_TO_MODIFY_OR_DELETE(CONFLICT, "04007", "수정 또는 삭제 하려는 객체에 대한 소유자가 아닙니다."),
    NOT_FOUND_USER(NOT_FOUND, "04008", "잘못된 사용자 ID입니다."),

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
