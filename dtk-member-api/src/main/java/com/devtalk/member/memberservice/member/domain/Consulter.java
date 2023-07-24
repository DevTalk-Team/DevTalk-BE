package com.devtalk.member.memberservice.member.domain;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@SuperBuilder
public class Consulter extends Member {

    @Column(nullable = false)
    private LocalDate birthDate;

}
