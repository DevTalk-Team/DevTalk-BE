package com.devtalk.member.memberservice.global.error.exception;

import com.devtalk.member.memberservice.global.error.ErrorCode;

public abstract class AbstractBusinessLogicException extends AbstractErrorException {
    public AbstractBusinessLogicException(ErrorCode errorCode) {
        super(errorCode);
    }
}
