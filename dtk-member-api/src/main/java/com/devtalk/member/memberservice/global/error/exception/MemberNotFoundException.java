package com.devtalk.member.memberservice.global.error.exception;

import com.devtalk.member.memberservice.global.error.ErrorCode;

public class MemberNotFoundException extends AbstractBusinessLogicException {
    public MemberNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}
