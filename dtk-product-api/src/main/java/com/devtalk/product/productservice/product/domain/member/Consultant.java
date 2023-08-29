package com.devtalk.product.productservice.product.domain.member;

import com.devtalk.product.productservice.product.domain.product.ReservedProceedType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("CONSULTER")
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Consultant extends Member {

    private int NF2F;

    private int F2F;

    private String area;

    public static Consultant createConsulter(Long memberId, String loginId, String name, RoleType roleType,
                                             int NF2F, int F2F, String area) {
        return Consultant.builder()
                .id(memberId)
                .loginId(loginId)
                .name(name)
                .role(roleType)
                .NF2F(NF2F)
                .F2F(F2F)
                .area(area)
                .build();
    }
    public int getPrice(ReservedProceedType reservedProceedType) {
        if (reservedProceedType == ReservedProceedType.NF2F) {
            return this.NF2F;
        }
        if (reservedProceedType == ReservedProceedType.F2F){
            return this.F2F;
        }
        else
            return 0;
    }
}

