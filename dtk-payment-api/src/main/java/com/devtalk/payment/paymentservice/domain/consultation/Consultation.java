package com.devtalk.payment.paymentservice.domain.consultation;

import com.devtalk.payment.paymentservice.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Table
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Consultation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    // 예약ID
//    @Column(nullable = false, unique = true)
//    private String consultationId;

    // 예약 번호
//    @Column(nullable = false)
//    private String consultationUid;

    // 의뢰자 이름
    @Column(nullable = false)
    private String consulter;

    // 의뢰자 이메일
    @Column(nullable = false)
    private String consulterEmail;

    // 전문가 이름
    @Column(nullable = false)
    private String consultant;

    // 상담 유형
    @Column(nullable = false)
    private String consultationType;

    // 결제 금액
    @Column(nullable = false)
    private Integer cost;

    // 상담 일시
    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP") // 받아와야 할 값임 ! 수정 필요(지금은 현재 시간으로 임의로 저장)
    private LocalDateTime consultationAt;

    // 상담 진행 상태
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProcessStatus processStatus;

    @Builder
    public Consultation(String consulter, String consulterEmail
                        , String consultant, String consultationType, Integer cost,
                        LocalDateTime consultationAt, ProcessStatus processStatus) {
        this.consulter = consulter;
        this.consulterEmail = consulterEmail;
        this.consultant = consultant;
        this.consultationType = consultationType;
        this.cost = cost;
        this.consultationAt = consultationAt;
        this.processStatus = processStatus;
    }
}
