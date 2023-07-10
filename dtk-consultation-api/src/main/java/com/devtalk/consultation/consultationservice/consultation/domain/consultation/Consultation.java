package com.devtalk.consultation.consultationservice.consultation.domain.consultation;

import com.devtalk.consultation.consultationservice.consultation.domain.member.Consulter;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    @OneToMany(mappedBy = "consultation", fetch = FetchType.LAZY)
    private List<ReservedItem> reservedItemList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "consultation", fetch = FetchType.LAZY)
    private List<CanceledItem> canceledItemList = new ArrayList<>();


}
