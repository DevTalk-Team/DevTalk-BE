package com.devtalk.board.consultationboardservice.global.error.exception;


import com.devtalk.board.consultationboardservice.global.error.ErrorCode;

public class NotFoundException extends BusinessRuleException {
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
