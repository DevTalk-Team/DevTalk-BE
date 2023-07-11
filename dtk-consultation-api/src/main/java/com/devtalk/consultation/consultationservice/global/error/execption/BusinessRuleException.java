package com.devtalk.consultation.consultationservice.global.error.execption;

import com.devtalk.consultation.consultationservice.global.error.ErrorCode;

public class BusinessRuleException extends AbstractErrorException {
    public BusinessRuleException(ErrorCode errorCode) {
        super(errorCode);
    }
}
