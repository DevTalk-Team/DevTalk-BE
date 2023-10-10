package com.devtalk.consultation.consultationservice.consultation.domain.consultation;

import com.devtalk.consultation.consultationservice.consultation.domain.BaseEntity;
import com.devtalk.consultation.consultationservice.global.vo.BaseTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AttachedFile extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attached_file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;

    private String fileUrl;

    private String originFileName;

    private String storedFileName;

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }

    public static AttachedFile createAttachedFile(Consultation consultation, String fileUrl, String originFileName, String storedFileName) {
        return AttachedFile.builder()
                .consultation(consultation)
                .fileUrl(fileUrl)
                .originFileName(originFileName)
                .storedFileName(storedFileName)
                .build();
    }
}
