package com.devtalk.payment.paymentservice.domain.payment;

public enum PaymentStatus {
    PAID, // 결제됨
    READY, // 결제 준비됨
    CANCEL // 결제 취소됨(환불)
}
