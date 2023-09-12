package com.devtalk.member.memberservice.member.domain.region;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Region {
    @Id
    @Column(name = "region_id")
    Long id;

    String region;
}
