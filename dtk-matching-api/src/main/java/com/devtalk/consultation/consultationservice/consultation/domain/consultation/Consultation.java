package com.devtalk.consultation.consultationservice.consultation.domain.consultation;

import com.devtalk.consultation.consultationservice.global.vo.BaseTime;
import com.devtalk.consultation.consultationservice.global.vo.Money;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.devtalk.consultation.consultationservice.consultation.domain.consultation.ProcessStatus.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Consultation extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consultation_id")
    private Long id;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Long consulterId;

    @Column(nullable = false)
    private String consulterName;

    @Column(nullable = false)
    private Long consultantId;

    @Column(nullable = false)
    private String consultantName;

    @Column(unique = true)
    private Long paymentId;

    @Embedded
    private ConsultationDetails consultationDetails;

    @Column(nullable = false, length = 20)
    private ProcessStatus status;

    @Column(nullable = false)
    private Money cost;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(nullable = false)
    private boolean canceled = false;

    @Builder
    public Consultation (Long consulterId, String consulterName,
                         Long consultantId, String consultantName,
                         Long productId, ConsultationDetails consultationDetails,
                         Integer cost) {
        this.productId = productId;
        this.consulterId = consulterId;
        this.consulterName = consulterName;
        this.consultantId = consultantId;
        this.consultantName = consultantName;
        this.consultationDetails = consultationDetails;
        this.cost = Money.of(cost);
    }

    public static Consultation createConsultation(Long consulterId, String consulterName,
                                                  Long consultantId, String consultantName,
                                                  Long productId, ConsultationDetails consultationDetails,
                                                  Integer cost) {
        Consultation newConsultation = Consultation.builder()
                .consulterId(consulterId)
                .consulterName(consulterName)
                .consultantId(consultantId)
                .consultantName(consultantName)
                .productId(productId)
                .consultationDetails(consultationDetails)
                .cost(cost)
                .build();
        newConsultation.changeStatus(ACCEPT_WAIT);
        return newConsultation;
    }

    public void changeStatus(ProcessStatus status) {
        this.status = status;
    }

}
