package com.devtalk.payment.paymentservice.domain.consultation;

public enum ProcessStatus {
    WAITING_APPROVAL, // 승인 대기중
    APPROVED, // 승인 완료
    WAITING_CONSULTATION // 상담 대기중
}
