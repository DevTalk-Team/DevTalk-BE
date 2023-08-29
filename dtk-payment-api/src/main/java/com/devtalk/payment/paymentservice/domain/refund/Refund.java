package com.devtalk.payment.paymentservice.domain.refund;

import com.devtalk.payment.paymentservice.domain.BaseEntity;
import com.devtalk.payment.paymentservice.domain.consultation.Consultation;
import com.devtalk.payment.paymentservice.domain.payment.Payment;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Refund extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 결제ID
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment paymentId;

    // 예약ID
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultation_id")
    private Consultation consultationId;

//    // 환불 사유
//    @Column(nullable = false, length = 100)
//    private String refundReason;

    // 환불 금액
    @Column(nullable = false)
    private Integer refundCost;
}
