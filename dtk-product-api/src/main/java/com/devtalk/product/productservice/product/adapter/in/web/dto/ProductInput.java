package com.devtalk.product.productservice.product.adapter.in.web.dto;

import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq;
import com.devtalk.product.productservice.product.domain.product.ConsultationType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.devtalk.product.productservice.product.application.port.in.dto.ProductReq.*;


public class ProductInput {
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class RegistrationInput{
        private Long consultantId;

        private  String status;

        //상담시간
        private  LocalDateTime reservationAt;

        //상담유형
        private ConsultationType type;


        public  RegistProdReq toReq(Long consultantId){
            return RegistProdReq.builder()
                    .consultantId(consultantId)
                    .status(status)
                    .reservationAt(reservationAt)
                    .type(type)
                    .build();
        }
    }
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ReservationInput {
        private Long productId;

        private Long consultantId;

        private Long consulterId;
        private String status;

        private LocalDateTime reservationAt;

        private ConsultationType type;

        private Long price;

        private String area;


        public ReserveProdReq toReq(Long productId) {
            return ReserveProdReq.builder()
                    .consultantId(consultantId)
                    .consulterId(consulterId)
                    .productId(productId)
                    .status(status)
                    .reservationAt(reservationAt)
                    .type(type)
                    .price(price)
                    .area(area)
                    .build();
        }
    }
}
