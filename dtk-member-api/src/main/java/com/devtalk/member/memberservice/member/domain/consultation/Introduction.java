package com.devtalk.member.memberservice.member.domain.consultation;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Introduction {
    @Column(name = "SELF_INTRODUCTION")
    private String selfIntroduction;
    private String career;
    private String field;
    private String skill;
}
