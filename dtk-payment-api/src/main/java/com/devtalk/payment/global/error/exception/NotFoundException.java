package com.devtalk.payment.global.error.exception;

import com.devtalk.payment.global.error.ErrorCode;

public class NotFoundException extends BusinessRuleException {
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
