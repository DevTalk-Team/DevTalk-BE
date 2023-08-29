package com.devtalk.payment.global.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {


    NOT_FOUND_CONSULTATION(NOT_FOUND, "03001", "존재하지 않는 예약ID 입니다."),
    NOT_FOUND_PAYMENT_INFO(NOT_FOUND, "03002", "결제 내역이 없습니다."),

    ALREADY_REFUNDED_CONSULTATION(CONFLICT, "03003", "결제 취소에 실패했습니다. 기 취소된 건입니다."),
    NOT_FOUND_REFUNDABLE_CONSULTATION(NOT_FOUND, "03004", "결제 취소에 실패했습니다. 취소할 결제건이 존재하지 않습니다."),
    INVALID_REFUND_REQUEST(CONFLICT, "03005", "잘못된 결제 취소 요청입니다"),

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus httpStatus, final String code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
