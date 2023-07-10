package com.devtalk.consultation.consultationservice.consultation.domain.consultation;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("RESERVED")
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReservedItem extends LinkItem {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;

    // TODO: Review 엔티티에서 id참조를 하고 있어서 mappedBy가 되는지 확인해봐야 함.
    @OneToOne(mappedBy = "consultationItemId", fetch = FetchType.LAZY)
    private Review review;


}
