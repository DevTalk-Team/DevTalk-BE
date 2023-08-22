package com.devtalk.payment.paymentservice.domain.refund;

import com.devtalk.payment.paymentservice.domain.BaseEntity;
import com.devtalk.payment.paymentservice.domain.consultation.Consultation;
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

    // 환불ID
    @Column(nullable = false, unique = true)
    private String refundId;

    // 결제ID
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "payment_id")
    @Column(nullable = false)
    private String paymentId;

    // 예약ID
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultation_id")
    private Consultation consultationId;

    // 환불 사유
    @Column(nullable = false, length = 100)
    private String text;

    // 환불 일시
    @Column(nullable = false, updatable = false, insertable = false)
    private Date refundAt;

    // 환불 금액
    @Column(nullable = false)
    private Integer refundCost;
}
