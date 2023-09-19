package com.devtalk.board.consultationboardservice.global.error.exception;

import com.devtalk.board.consultationboardservice.global.error.ErrorCode;

public class BusinessRuleException extends AbstractErrorException {
    public BusinessRuleException(ErrorCode errorCode) {
        super(errorCode);
    }
}
