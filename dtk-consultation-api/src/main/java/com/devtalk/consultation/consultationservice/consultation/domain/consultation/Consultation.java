package com.devtalk.consultation.consultationservice.consultation.domain.consultation;

import com.devtalk.consultation.consultationservice.consultation.domain.member.Consultant;
import com.devtalk.consultation.consultationservice.consultation.domain.member.Consulter;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.devtalk.consultation.consultationservice.consultation.domain.consultation.ReservedItem.*;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Consultation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulter_id")
    private Consulter consulter;

    @Builder.Default
    @OneToMany(mappedBy = "consultation", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ReservedItem> reservedItemList = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "consultation", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CanceledItem> canceledItemList = new HashSet<>();

    public static Consultation createConsultation(Consulter consulter) {
        return Consultation.builder()
                .consulter(consulter)
                .build();
    }

    public void addReservedItem(ReservedItem reservedItem) {
        reservedItemList.add(reservedItem);
        reservedItem.changeConsultation(this);
    }

    public void reserve(Consultant consultant, Long productId, ProcessMean mean,
                        String largeArea, String detailArea, LocalDateTime reservationAT,
                        String content, List<AttachedFile> attachedFileList, Integer cost) {
        this.addReservedItem(createReservedItem(consultant, productId, mean, largeArea, detailArea,
                        reservationAT, content, attachedFileList, cost));
    }
}
