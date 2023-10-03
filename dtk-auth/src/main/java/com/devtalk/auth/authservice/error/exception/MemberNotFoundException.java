package com.devtalk.auth.authservice.error.exception;

import com.devtalk.auth.authservice.error.ErrorCode;

public class MemberNotFoundException extends AbstractBusinessLogicException {
    public MemberNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}
