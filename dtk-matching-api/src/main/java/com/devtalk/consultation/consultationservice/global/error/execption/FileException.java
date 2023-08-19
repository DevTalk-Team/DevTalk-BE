package com.devtalk.consultation.consultationservice.global.error.execption;

import com.devtalk.consultation.consultationservice.global.error.ErrorCode;

public class FileException extends BusinessRuleException {
    public FileException(ErrorCode errorCode) {
        super(errorCode);
    }
}
