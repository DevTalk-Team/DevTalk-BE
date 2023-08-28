package com.devtalk.member.memberservice.global.error.exception;

import com.devtalk.member.memberservice.global.error.ErrorCode;

public class TokenException extends AbstractErrorException {
    public TokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
