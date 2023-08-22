package com.devtalk.consultation.consultationservice.global.error.execption;

import com.devtalk.consultation.consultationservice.global.error.ErrorCode;

public class InvalidInputException extends BusinessRuleException {

    public InvalidInputException(ErrorCode errorCode) {
        super(errorCode);
    }
}
