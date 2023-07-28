package com.devtalk.payment.global.vo;

public class ResponseData<T> {
    private String code;
    private String message;
    private T result;

    public ResponseData(String code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }
}
