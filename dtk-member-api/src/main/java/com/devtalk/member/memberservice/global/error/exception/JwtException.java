package com.devtalk.member.memberservice.global.error.exception;

import com.devtalk.member.memberservice.global.error.ErrorCode;

public class JwtException extends AbstractBusinessLogicException {
    public JwtException(ErrorCode errorCode) {
        super(errorCode);
    }
}
