package com.devtalk.consultation.consultationservice.consultation.domain.consultation;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConsultationArea {

    @Column(nullable = false, length = 20)
    private String largeArea;

    @Column(nullable = false, length = 20)
    private String detailArea;
}
