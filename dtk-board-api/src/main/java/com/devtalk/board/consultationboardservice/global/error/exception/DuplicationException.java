package com.devtalk.board.consultationboardservice.global.error.exception;


import com.devtalk.board.consultationboardservice.global.error.ErrorCode;

public class DuplicationException extends BusinessRuleException {

    public DuplicationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
