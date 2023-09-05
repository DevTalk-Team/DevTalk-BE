package com.devtalk.member.memberservice.member.domain.category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Category {
    @Id
    @Column(name = "category_id")
    Long id;

    String category;
}
