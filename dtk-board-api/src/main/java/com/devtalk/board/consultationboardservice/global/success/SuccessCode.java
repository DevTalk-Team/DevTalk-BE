package com.devtalk.board.consultationboardservice.global.success;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.OK;

@Getter
public enum SuccessCode {
    // 게시글 등록
    BOARD_POST_SUCCESS(OK, "0400", "게시글이 등록되었습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    SuccessCode(final HttpStatus httpStatus, final String code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
