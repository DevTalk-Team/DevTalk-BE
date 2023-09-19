package com.devtalk.board.consultationboardservice.global.error.exception;


import com.devtalk.board.consultationboardservice.global.error.ErrorCode;

public class InvalidInputException extends BusinessRuleException {

    public InvalidInputException(ErrorCode errorCode) {
        super(errorCode);
    }
}
