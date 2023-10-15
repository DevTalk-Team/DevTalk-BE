package com.devtalk.member.memberservice.global.error.exception;

import com.devtalk.member.memberservice.global.error.ErrorCode;

public class JwtException extends AbstractErrorException {
    public JwtException(ErrorCode errorCode) {
        super(errorCode);
    }
}
