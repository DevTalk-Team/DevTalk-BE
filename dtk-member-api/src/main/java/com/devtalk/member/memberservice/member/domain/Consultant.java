package com.devtalk.member.memberservice.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
public class Consultant extends Member {

    @Column(nullable = false)
    private String company;

}
