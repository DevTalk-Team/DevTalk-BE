package com.devtalk.common.commonservice.error.exception;


import com.devtalk.common.commonservice.error.ErrorCode;

public class TokenException extends AbstractErrorException {
    public TokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
