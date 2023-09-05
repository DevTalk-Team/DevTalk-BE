package com.devtalk.member.memberservice.member.domain.consultation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ConsultationType {
    @Id
    @Column(name = "CONSULTATION_TYPE_ID")
    private Long id;

    @Column(name = "CONSULTATION_TYPE")
    private String consultationType;
}
