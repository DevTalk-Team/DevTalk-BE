package com.devtalk.payment.paymentservice.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "consultation")
@Data
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 예약ID
    @Column(nullable = false, unique = true)
    private String consultationId;

    // 의뢰자 이름
    @Column(nullable = false)
    private String counselee;

    // 전문가 이름
    @Column(nullable = false)
    private String counselor;

    // 상담 유형
    @Column(nullable = false)
    private String consultationType;

    // 상담 일시
    @Column(nullable = false, updatable = false, insertable = false)
    private Date consultationAt;

    // 상담 진행 상태
    @Column(nullable = false)
    private String processStatus;
}
