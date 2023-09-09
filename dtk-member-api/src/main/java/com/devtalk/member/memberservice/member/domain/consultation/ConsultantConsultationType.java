package com.devtalk.member.memberservice.member.domain.consultation;

import com.devtalk.member.memberservice.member.domain.member.Member;
import jakarta.persistence.*;

@Entity
public class ConsultantConsultationType {
    @Id @GeneratedValue
    @Column(name = "CONSULTANT_CONSULTATION_TYPE")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONSULTANT_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONSULTATION_TYPE_ID")
    private ConsultationType consultationType;
}
