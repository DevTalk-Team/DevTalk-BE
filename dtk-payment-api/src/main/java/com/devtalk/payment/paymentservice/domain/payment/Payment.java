package com.devtalk.payment.paymentservice.domain.payment;

import com.devtalk.payment.paymentservice.domain.BaseEntity;
import com.devtalk.payment.paymentservice.domain.consultation.Consultation;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    // 예약ID
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;

    // 포트원 거래고유 번호
    @Column
    private String paymentUid;

    // 포트원 가맹점 상품 고유번호
    @Column
    private String merchantId;

    // 결제 금액
    @Column(nullable = false)
    private Integer cost;

    // 결제 일시
    @Column
    private LocalDateTime paidAt;

    // 결제 상태
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    public void changePaymentBySuccess(String paymentUid) {
        this.status = PaymentStatus.PAID;
        this.paymentUid = paymentUid;
        this.paidAt = LocalDateTime.now();
    }

    public void changePaymentByCanceled() {
        this.status = PaymentStatus.CANCELED;
    }
}
