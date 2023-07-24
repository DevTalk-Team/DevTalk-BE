package com.devtalk.payment.global.error.exception;

import com.devtalk.payment.global.error.ErrorCode;

public class BusinessRuleException extends AbstractErrorException {
    public BusinessRuleException(ErrorCode errorCode) {
        super(errorCode);
    }
}
