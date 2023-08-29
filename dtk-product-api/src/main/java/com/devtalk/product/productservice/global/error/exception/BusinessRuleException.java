package com.devtalk.product.productservice.global.error.exception;

import com.devtalk.product.productservice.global.error.ErrorCode;

public class BusinessRuleException extends AbstractErrorException {
    public BusinessRuleException(ErrorCode errorCode) {
        super(errorCode);
    }
}
