package com.devtalk.auth.authservice.error.exception;

import com.devtalk.auth.authservice.error.ErrorCode;

public abstract class AbstractBusinessLogicException extends AbstractErrorException {
    public AbstractBusinessLogicException(ErrorCode errorCode) {
        super(errorCode);
    }
}
