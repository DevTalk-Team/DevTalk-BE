package com.devtalk.consultation.consultationservice.consultation.domain.consultation;

import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.ConsultationCancellationRepo;
import com.devtalk.consultation.consultationservice.global.error.ErrorCode;
import com.devtalk.consultation.consultationservice.global.error.execption.BusinessRuleException;
import com.devtalk.consultation.consultationservice.global.vo.BaseTime;
import com.devtalk.consultation.consultationservice.global.vo.Money;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.devtalk.consultation.consultationservice.consultation.domain.consultation.ConsultationCancellation.*;
import static com.devtalk.consultation.consultationservice.consultation.domain.consultation.ProcessStatus.*;
import static com.devtalk.consultation.consultationservice.global.error.ErrorCode.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
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

    @OneToOne(mappedBy = "consultation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)

    private ConsultationCancellation consultationCancellation;

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
        newConsultation.setStatus(ACCEPT_WAIT);
        return newConsultation;
    }

    public void setStatus(ProcessStatus status) {
        this.status = status;
    }

    public void changeConsultationCancellation(ConsultationCancellation consultationCancellation) {
        if (this.consultationCancellation != null) {
            this.consultationCancellation.setConsultation(null);
        }
        this.consultationCancellation = consultationCancellation;
        this.consultationCancellation.setConsultation(this);
    }

    public void changeAttachedFileList(List<AttachedFile> newAttachedFileList) {
        List<AttachedFile> originAttachedFileList = this.consultationDetails.getAttachedFileList();
        if (originAttachedFileList != null) {
            originAttachedFileList.stream().forEach(attachedFile -> attachedFile.setConsultation(null));
        }
        this.consultationDetails.setAttachedFileList(newAttachedFileList);
        newAttachedFileList.stream().forEach(attachedFile -> attachedFile.setConsultation(this));
    }

    public void accept() {
        this.status = ACCEPTED;
    }

    public void modifyDetails(String newContent, List<AttachedFile> newAttachedFileList) {
        changeAttachedFileList(newAttachedFileList);
        this.consultationDetails.setContent(newContent);
    }

    public ConsultationCancellation cancelByConsulter(String canceledReason) {
        if (!(this.status.equals(ACCEPT_WAIT) || this.status.equals(ACCEPTED) || this.status.equals(PAID))) {
            throw new BusinessRuleException(IRREVOCABLE_STATUS);
        }
        return cancel(CONSULTER_CANCELED, canceledReason);
    }

        //TODO: 상담사가 상담취소를 하는데 케이스가 2가지가 있음
        // 1. 그냥 처음부터 요청들어오면 거절하는것
        // 2. 승인 후에 취소하는 것 (결제전, 결제후)
    public ConsultationCancellation cancelByConsultant(String canceledReason) {
        ProcessStatus newStatus = null;
        if (this.status.equals(ACCEPT_WAIT)) {
            newStatus = CONSULTANT_REFUSED;
        } else if (this.status.equals(PAID) || this.status.equals(ACCEPTED)) {
            newStatus = CONSULTANT_CANCELED;
        } else {
            throw new BusinessRuleException(IRREVOCABLE_STATUS);
        }
        return cancel(newStatus, canceledReason);
    }

    public void writeReview(Integer score, String content,
                            String photoUrl, String photoOriginName, String photoStoredName, LocalDate reviewAt) {
        if (!this.status.equals(PAID)
                && this.consultationDetails.getReservationDate().isAfter(reviewAt)
                && this.consultationDetails.getReservationDate().plusDays(7).isBefore(reviewAt)
                && this.review != null) {
            throw new BusinessRuleException(REVIEW_IMPOSSIBLE_STATUS);
        }
        this.review = Review.createReview(this.consultantId, this.consulterName, this.consultantId, this.consultantName, score, content);
    }

    private ConsultationCancellation cancel(ProcessStatus status, String canceledReason) {
        this.status = status;
        this.canceled = true;

        ConsultationCancellation consultationCancellation = createConsultationCancellation(this.productId, canceledReason);

        changeConsultationCancellation(consultationCancellation);

        // 변경된 상담 취소 객체를 반환합니다.
        return consultationCancellation;
    }
}
