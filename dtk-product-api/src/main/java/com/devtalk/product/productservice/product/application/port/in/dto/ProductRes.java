package com.devtalk.product.productservice.product.application.port.in.dto;

import com.devtalk.product.productservice.product.domain.product.Product;
import com.devtalk.product.productservice.product.domain.product.ProductProceedType;
import com.devtalk.product.productservice.product.domain.product.ReservedProceedType;
import com.devtalk.product.productservice.product.domain.product.ProductReservedDetails;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter

public class ProductRes {
    @Getter
    @Builder
    public static class ConsultantProductListRes{
        private Long productId;
        private Long consultantId;
        private String status;
        private LocalDateTime reservationAt;
        private ProductProceedType productProceedType;
    }




    @Getter
    @Builder
    public static class ReservedProductRes {
        private Long productId;
        private Long consultationId;
        private Long consultantId;
        private Long consulterId;
        private String status;
        private LocalDateTime reservationAt;
        private int price;
        private ReservedProceedType reservedProceedType;
        private String area;
    }

    public static ReservedProductRes loadInfo(ProductReservedDetails productReservedDetails){
        return ReservedProductRes.builder()
                .productId(productReservedDetails.getProduct().getId())
                .consultationId(productReservedDetails.getId())
                .consultantId(productReservedDetails.getProduct().getConsultant().getId())
                .consulterId(productReservedDetails.getConsulterId())
                .status("예약 중")
                .reservationAt(productReservedDetails.getProduct().getReservationAt())
                .price(productReservedDetails.getPrice())
                .reservedProceedType(productReservedDetails.getReservedProceedType())
                .area(productReservedDetails.getArea())
                .build();
    }
}
