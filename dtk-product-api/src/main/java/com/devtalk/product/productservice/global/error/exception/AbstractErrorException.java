package com.devtalk.product.productservice.global.error.exception;

import com.devtalk.product.productservice.global.error.ErrorCode;
import lombok.Getter;

@Getter
public abstract class AbstractErrorException extends RuntimeException{
    private final ErrorCode errorCode;

    public AbstractErrorException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}