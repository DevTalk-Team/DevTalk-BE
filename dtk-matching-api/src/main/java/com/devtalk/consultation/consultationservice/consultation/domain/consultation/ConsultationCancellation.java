package com.devtalk.consultation.consultationservice.consultation.domain.consultation;

import com.devtalk.consultation.consultationservice.global.vo.BaseTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ConsultationCancellation extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consultation_cancellation_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false, length = 500)
    private String canceledReason;

    public static ConsultationCancellation createConsultationCancellation(Long productId, String canceledReason) {
        return ConsultationCancellation.builder()
                .productId(productId)
                .canceledReason(canceledReason)
                .build();

    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }
}
