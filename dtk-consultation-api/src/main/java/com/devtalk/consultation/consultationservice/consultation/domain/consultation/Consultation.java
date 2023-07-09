package com.devtalk.consultation.consultationservice.consultation.domain.consultation;

import com.devtalk.consultation.consultationservice.consultation.domain.member.Consulter;
import jakarta.persistence.*;
import lombok.*;

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

}
