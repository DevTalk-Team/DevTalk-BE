package com.devtalk.product.productservice.product.application.port.in.dto;

import com.devtalk.product.productservice.product.domain.product.ConsultationType;
import com.devtalk.product.productservice.product.domain.product.Product;
import com.devtalk.product.productservice.product.domain.product.ReservedDetails;
import com.devtalk.product.productservice.product.domain.product.ReservedType;
import lombok.*;

import java.time.LocalDateTime;

public class ProductRes {
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ReservedProductRes {
        private Long productId;
        private Long consultationId;
        private Long consultantId;
        private Long consulterId;
        private String status;
        private LocalDateTime reservationAt;
        private int price;
        private ReservedType reservedType;
        private String area;
    }
    public static ReservedProductRes loadInfo(ReservedDetails reservedDetails){
        return ReservedProductRes.builder()
                .productId(reservedDetails.getProduct().getId())
                .consultationId(reservedDetails.getId())
                .consultantId(reservedDetails.getProduct().getConsultant().getId())
                .consulterId(reservedDetails.getConsulterId())
                .status("예약 중")
                .reservationAt(reservedDetails.getProduct().getReservationAt())
                .price(reservedDetails.getPrice())
                .reservedType(reservedDetails.getReservedType())
                .area(reservedDetails.getArea())
                .build();
    }
}
