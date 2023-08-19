package com.devtalk.consultation.consultationservice.consultation.domain.consultation;

import com.devtalk.consultation.consultationservice.consultation.domain.member.Consultant;
import com.devtalk.consultation.consultationservice.global.vo.Money;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("RESERVED")
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReservedItem extends LinkItem {

    @Column(unique = true, nullable = false)
    private Long productId;

    // TODO: Review 엔티티에서 id참조를 하고 있어서 mappedBy가 되는지 확인해봐야 함.
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;

    @Builder.Default
    @OneToMany(mappedBy = "reservedItem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AttachedFile> attachedFileList = new ArrayList<>();

    public void changeConsultation(Consultation consultation) {
        this.consultation = consultation;
    }

    public static ReservedItem createReservedItem(Consultant consultant, Long productId, ProcessMean mean,
                                                  String largeArea, String detailArea, LocalDateTime reservationAT,
                                                  String content, List<AttachedFile> attachedFileList, Integer cost) {
        return ReservedItem.builder()
                .consultant(consultant)
                .productId(productId)
                .processMean(mean)
                .category(new Category(largeArea, detailArea))
                .reservationAT(reservationAT)
                .content(content)
                .attachedFileList(attachedFileList)
                .cost(Money.of(cost))
                .status(ProcessStatus.ACCEPT_WAIT)
                .build();
    }
}
