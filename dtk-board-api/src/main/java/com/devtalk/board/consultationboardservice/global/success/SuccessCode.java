package com.devtalk.board.consultationboardservice.global.success;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.OK;

@Getter
public enum SuccessCode {
    // 게시글 등록
    BOARD_POST_SUCCESS(OK, "0400", "게시글 등록 성공"),
    BOARD_VIEW_SUCCESS(OK, "0401", "해당 게시글 조회 성공"),
    USER_POST_LIST_SEARCH_SUCCESS(OK, "0402", "해당 사용자 게시글 조회 성공")
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
