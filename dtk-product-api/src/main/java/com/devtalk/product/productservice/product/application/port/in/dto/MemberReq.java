package com.devtalk.product.productservice.product.application.port.in.dto;

import com.devtalk.product.productservice.product.domain.member.MemberType;
import com.devtalk.product.productservice.product.domain.product.ProductProceedType;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberReq {
    private MemberType memberType;
    private String name;
    private String phoneNumber;
    private int NF2F_Price;
    private int F2F_Price;
    private String region;
}