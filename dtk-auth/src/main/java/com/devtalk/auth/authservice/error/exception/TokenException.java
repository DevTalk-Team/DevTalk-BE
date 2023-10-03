package com.devtalk.auth.authservice.error.exception;


import com.devtalk.auth.authservice.error.ErrorCode;

public class TokenException extends AbstractErrorException {
    public TokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
