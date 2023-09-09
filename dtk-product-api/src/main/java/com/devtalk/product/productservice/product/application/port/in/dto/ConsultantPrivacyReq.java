package com.devtalk.product.productservice.product.application.port.in.dto;

import com.devtalk.product.productservice.product.domain.member.MemberType;
import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ConsultantPrivacyReq{
    private Long id;
    private int NF2F_Price;
    private int F2F_Price;
    private String region;
}