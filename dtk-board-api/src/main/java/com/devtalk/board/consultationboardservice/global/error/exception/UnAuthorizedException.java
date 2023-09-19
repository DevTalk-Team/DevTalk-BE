package com.devtalk.board.consultationboardservice.global.error.exception;


import com.devtalk.board.consultationboardservice.global.error.ErrorCode;

public class UnAuthorizedException extends BusinessRuleException {
    public UnAuthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
