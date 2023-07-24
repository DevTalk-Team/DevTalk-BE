package com.devtalk.payment.paymentservice.domain.payment;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Entity
@Table
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 결제ID
    @Column(nullable = false, unique = true)
    private String paymentId;

    // 예약ID
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "consultation_id")
    @Column(nullable = false)
    private String consultationId;

    // 거래고유 번호
    @Column(nullable = false)
    private String impUid;

    // 결제 PG사 코드
    @Column(nullable = false)
    private String paymentPgId;

    // 결제 금액
    @Column(nullable = false)
    private Integer cost;

    // 결제 일시
    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date paidAt;

    // 결제 상태
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}
