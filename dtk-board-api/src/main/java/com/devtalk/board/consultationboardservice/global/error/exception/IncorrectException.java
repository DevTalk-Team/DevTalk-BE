package com.devtalk.board.consultationboardservice.global.error.exception;


import com.devtalk.board.consultationboardservice.global.error.ErrorCode;

public class IncorrectException extends BusinessRuleException {
    public IncorrectException(ErrorCode errorCode) {
        super(errorCode);
    }
}
