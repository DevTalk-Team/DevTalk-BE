package com.devtalk.member.memberservice.global.error.exception;

import com.devtalk.member.memberservice.global.error.ErrorCode;

public class AuthCodeMismatchingException extends AbstractBusinessLogicException {
    public AuthCodeMismatchingException(ErrorCode errorCode) {
        super(errorCode);
    }
}
