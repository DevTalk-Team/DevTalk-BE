package com.devtalk.consultation.consultationservice.consultation.domain.consultation;

import com.devtalk.consultation.consultationservice.consultation.domain.member.Consulter;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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

    public void reserve() {
        ReservedItem reservedItem = ReservedItem.createReservedItem(this);
    }
}
