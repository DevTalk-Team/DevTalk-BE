package com.devtalk.member.memberservice.member.domain.consultation;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Price {
    private Integer call15m;
    private Integer call30m;
    private Integer video30m;
    private Integer f2f1h;
}
