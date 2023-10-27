package com.devtalk.member.memberservice.global.error.exception;

import com.devtalk.member.memberservice.global.error.ErrorCode;

public class FileException extends AbstractBusinessLogicException {
    public FileException(ErrorCode errorCode) {
        super(errorCode);
    }
}
