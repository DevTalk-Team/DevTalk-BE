package com.devtalk.common.commonservice.error.exception;

import com.devtalk.common.commonservice.error.ErrorCode;

public abstract class AbstractBusinessLogicException extends AbstractErrorException {
    public AbstractBusinessLogicException(ErrorCode errorCode) {
        super(errorCode);
    }
}
