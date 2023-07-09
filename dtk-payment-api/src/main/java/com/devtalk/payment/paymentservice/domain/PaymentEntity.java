package com.devtalk.payment.paymentservice.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Entity
@Table(name = "payment_info")
@Data
class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 결제ID
    @Column(nullable = false, unique = true)
    private String paymentId;

    // 예약ID
    @Column(nullable = false)
    private String consultationId;

    // 거래고유 번호
    @Column(nullable = false)
    private String impUID;

    // 결제 PG사 코드
    @Column(nullable = false)
    private String paymentPGID;

    // 결제 금액
    @Column(nullable = false)
    private Integer cost;

    // 결제 일시
    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date paidAt;

    // 결제 상태
    @Column(nullable = false)
    private String status;
}
