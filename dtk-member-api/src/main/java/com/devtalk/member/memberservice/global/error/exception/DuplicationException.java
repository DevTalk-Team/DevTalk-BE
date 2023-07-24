package com.devtalk.member.memberservice.global.error.exception;

import com.devtalk.member.memberservice.global.error.ErrorCode;

public class DuplicationException extends AbstractBusinessLogicException {
    public DuplicationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
