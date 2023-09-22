package com.devtalk.board.consultationboardservice.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public enum ErrorCode {
    NOT_FOUND_POST(NOT_FOUND, "04001", "해당 게시글 찾기 실패"),
    NOT_FOUND_COMMENT(NOT_FOUND, "04002", "해당 댓글 찾기 실패")
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
