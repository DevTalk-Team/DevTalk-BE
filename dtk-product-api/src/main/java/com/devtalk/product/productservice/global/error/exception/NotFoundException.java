package com.devtalk.product.productservice.global.error.exception;

import com.devtalk.product.productservice.global.error.ErrorCode;

public class NotFoundException extends BusinessRuleException {
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}
