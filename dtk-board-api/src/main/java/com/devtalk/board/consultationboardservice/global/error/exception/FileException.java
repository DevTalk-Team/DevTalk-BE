package com.devtalk.board.consultationboardservice.global.error.exception;


import com.devtalk.board.consultationboardservice.global.error.ErrorCode;

public class FileException extends BusinessRuleException {
    public FileException(ErrorCode errorCode) {
        super(errorCode);
    }
}
