package com.devtalk.member.memberservice.member.domain.field;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Field {

    @Id
    @GeneratedValue
    @Column(name = "field_id")
    Long id;

    @Column(name = "field_name")
    String fieldName;

}
