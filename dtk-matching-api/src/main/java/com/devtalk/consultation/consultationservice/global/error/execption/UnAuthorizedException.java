package com.devtalk.consultation.consultationservice.global.error.execption;


import com.devtalk.consultation.consultationservice.global.error.ErrorCode;

public class UnAuthorizedException extends BusinessRuleException {
    public UnAuthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
