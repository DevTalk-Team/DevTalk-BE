package com.devtalk.consultation.consultationservice.global.error.execption;

import com.devtalk.consultation.consultationservice.global.error.ErrorCode;

public class DuplicationException extends BusinessRuleException {

    public DuplicationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
