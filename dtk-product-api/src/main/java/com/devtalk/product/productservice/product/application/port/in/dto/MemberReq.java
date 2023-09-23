package com.devtalk.product.productservice.product.application.port.in.dto;

import lombok.*;

import java.time.LocalDateTime;

public class MemberReq {
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ConsultantPriceReq {
        private Long consultantId;
        private Integer NF2F_Cost;
        private Integer F2F_Cost;
    }
}