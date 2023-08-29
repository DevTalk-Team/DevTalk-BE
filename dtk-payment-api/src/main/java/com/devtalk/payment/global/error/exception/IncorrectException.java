package com.devtalk.payment.global.error.exception;

import com.devtalk.payment.global.code.ErrorCode;

public class IncorrectException extends BusinessRuleException{
    public IncorrectException(ErrorCode errorCode) {
        super(errorCode);
    }
}
