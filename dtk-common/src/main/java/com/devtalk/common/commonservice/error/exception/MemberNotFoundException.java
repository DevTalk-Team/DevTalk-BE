package com.devtalk.common.commonservice.error.exception;

import com.devtalk.common.commonservice.error.ErrorCode;

public class MemberNotFoundException extends AbstractBusinessLogicException {
    public MemberNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}
