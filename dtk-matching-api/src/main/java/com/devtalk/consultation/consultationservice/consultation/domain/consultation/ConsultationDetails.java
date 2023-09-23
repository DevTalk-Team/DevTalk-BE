package com.devtalk.consultation.consultationservice.consultation.domain.consultation;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConsultationDetails {

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private ProductProceedType productProceedType;

    @Column(nullable = false, length = 20)
    private String region;


    @Column(nullable = false, length = 30)
    private LocalDateTime reservationAT;

    @Column(length = 500)
    private String content;

    @Builder.Default
    @OneToMany(mappedBy = "consultation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AttachedFile> attachedFileList = new ArrayList<>();

    public void setAttachedFileList(List<AttachedFile> attachedFileList) {
        this.attachedFileList = attachedFileList;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
