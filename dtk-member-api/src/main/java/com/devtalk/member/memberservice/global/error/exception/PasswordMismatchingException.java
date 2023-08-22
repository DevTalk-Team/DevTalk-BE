package com.devtalk.member.memberservice.global.error.exception;

import com.devtalk.member.memberservice.global.error.ErrorCode;

public class PasswordMismatchingException extends AbstractBusinessLogicException {
    public PasswordMismatchingException(ErrorCode errorCode) {
        super(errorCode);
    }
}
