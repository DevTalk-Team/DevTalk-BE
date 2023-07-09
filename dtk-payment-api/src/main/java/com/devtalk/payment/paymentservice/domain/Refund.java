package com.devtalk.payment.paymentservice.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "refund")
@Data
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 환불ID
    @Column(nullable = false, unique = true)
    private String refundId;

    // 결제ID
    @Column(nullable = false)
    private String paymentId;

    // 예약ID
    @Column(nullable = false)
    private String consultationId;

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
