package com.devtalk.product.productservice.product.adapter.in.web.dto;

import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq;
import lombok.*;

import java.time.LocalDateTime;

import static com.devtalk.product.productservice.product.application.port.in.dto.ProductReq.*;


public class ProductInput {
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class RegistrationInput{

        private static String status;

        //상담시간
        private static LocalDateTime reservationAt;

        //상담유형
        private static String type;

        //상담 지역
        private static String area;

        //가격
        private static int price;


        public static RegistProdReq toReq(Long consultantId){
            return RegistProdReq.builder()
                    .consultantId(consultantId)
                    .status(status)
                    .reservationAt(reservationAt)
                    .type(type)
                    .area(area)
                    .price(price)
                    .build();
        }
    }

    public static class UpdateInput{

        private static String status;

        //상담시간
        private static LocalDateTime reservationAt;

        //상담유형
        private static String type;

        //상담 지역
        private static String area;

        //가격
        private static int price;


        public static UpdateProdReq toReq(Long consultantId){
            return UpdateProdReq.builder()
                    .consultantId(consultantId)
                    .status(status)
                    .reservationAt(reservationAt)
                    .type(type)
                    .area(area)
                    .price(price)
                    .build();
        }
    }
}
