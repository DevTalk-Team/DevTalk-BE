package com.devtalk.product.productservice.product.application.port.in.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RegistProdReq {

    private Long consultantId;
    private LocalDateTime reservationAt;
    private int type;
    private String status;
}


