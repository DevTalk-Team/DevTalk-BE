package com.devtalk.consultation.consultationservice.global.error.execption;

import com.devtalk.consultation.consultationservice.global.error.ErrorCode;

public class IncorrectException extends BusinessRuleException {
    public IncorrectException(ErrorCode errorCode) {
        super(errorCode);
    }
}
