package com.devtalk.payment.paymentservice.domain.consultation;

import com.devtalk.payment.paymentservice.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

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

    // 포트원 가맹점 상품 번호
    @Column
    private String merchantId;

    // 의뢰자ID
    @Column(nullable = false)
    private Long consulterId;

    // 의뢰자 이름
    @Column(nullable = false)
    private String consulterName;

    // 의뢰자 이메일
    @Column(nullable = false)
    private String consulterEmail;

    // 전문가ID
    @Column(nullable = false)
    private Long consultantId;

    // 전문가 이름
    @Column(nullable = false)
    private String consultantName;

    // 상담 유형
    @Column(nullable = false)
    private String consultationType;

    // 결제 금액
    @Column(nullable = false)
    private Integer cost;

    // 상담 일시
    @Column(nullable = false)
    private LocalDateTime consultationAt;

    // 상담 진행 상태
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProcessStatus processStatus;

    public static Consultation createConsultation(Long consulterId, String consulterName, String consulterEmail,
                                                  Long consultantId, String consultantName, String consultationType,
                                                  Integer cost, LocalDateTime consultationAt, ProcessStatus processStatus){
        return Consultation.builder()
                .merchantId(null)
                .consulterId(consulterId)
                .consulterName(consulterName)
                .consulterEmail(consulterEmail)
                .consultantId(consultantId)
                .consultantName(consultantName)
                .consultationType(consultationType)
                .cost(cost)
                .consultationAt(consultationAt)
                .processStatus(processStatus)
                .build();
    }

    public void changeConsultationByCanceled() {
        this.processStatus = ProcessStatus.CANCELED;
    }
    public void changeConsultationByWaitingConsultation(){
        this.processStatus = ProcessStatus.WAITING_CONSULTATION;}
}
