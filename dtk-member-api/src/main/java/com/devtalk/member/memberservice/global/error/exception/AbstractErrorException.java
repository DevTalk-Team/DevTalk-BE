package com.devtalk.member.memberservice.global.error.exception;

import com.devtalk.member.memberservice.global.error.ErrorCode;
import lombok.Getter;

@Getter
public abstract class AbstractErrorException extends RuntimeException {
    private final ErrorCode errorCode;

    public AbstractErrorException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
