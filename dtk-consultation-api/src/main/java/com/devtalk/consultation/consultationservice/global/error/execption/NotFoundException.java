package com.devtalk.consultation.consultationservice.global.error.execption;

import com.devtalk.consultation.consultationservice.global.error.ErrorCode;

public class NotFoundException extends BusinessRuleException {
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
