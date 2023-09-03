package com.devtalk.product.productservice.product.domain.member;

import com.devtalk.product.productservice.product.domain.product.ReservedProceedType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("CONSULTANT")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Consultant extends Member {

    private int NF2F;

    private int F2F;

    private String region;

//    public static Consultant createConsulter(Long memberId, String loginId, String name, MemberType memberType,
//                                             int NF2F, int F2F, String region) {
//        return Consultant.builder()
//                .id(memberId)
//                .loginId(loginId)
//                .name(name)
//                .memberType(memberType)
//                .NF2F(NF2F)
//                .F2F(F2F)
//                .region(region)
//                .build();
//    }
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

