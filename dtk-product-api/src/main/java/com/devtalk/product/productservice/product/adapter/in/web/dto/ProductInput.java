package com.devtalk.product.productservice.product.adapter.in.web.dto;

import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq;
import com.devtalk.product.productservice.product.domain.product.Product;
import com.devtalk.product.productservice.product.domain.product.ProductProceedType;
//import com.devtalk.product.productservice.product.domain.product.ReservedProceedType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ProductInput {
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class RegistrationInput{

        private Long consultantId;
        private String reserveDate;
        private String reserveTime;
        private ProductProceedType productProceedType;

        public ProductReq.RegistProdReq toReq( ){
            if (reserveDate == null || reserveTime == null) {
                throw new IllegalArgumentException("reservationDate and reservationTime must not be null");
            }
           LocalDate reservationDate = LocalDate.parse(reserveDate);
           LocalTime reservationTime = LocalTime.parse(reserveTime);
//            LocalDateTime reservationAt = LocalDateTime.of(date, time);

            return ProductReq.RegistProdReq.builder()
                    .memberId(consultantId)
                    .reservationDate(reservationDate)
                    .reservationTime(reservationTime)
                    .productProceedType(productProceedType)
                    .build();
        }
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class SearchInput{

        private Long consultantId;
        private String searchDate;
        private String searchTime;

        public ProductReq.SearchProdReq toReq( ){
            if (searchDate == null || searchTime == null) {
                throw new IllegalArgumentException("reservationDate and reservationTime must not be null");
            }
            LocalDate date = LocalDate.parse(searchDate);
            LocalTime time = LocalTime.parse(searchTime);
            //LocalDateTime reservationAt = LocalDateTime.of(date, time);

            return ProductReq.SearchProdReq.builder()
                    .memberId(consultantId)
                    .reservationDate(date)
                    .reservationTime(time)
                    .build();
        }
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class UpdateInput{

        private String updateDate;
        private String updateTime;
        private ProductProceedType type;

        public ProductReq.UpdateProdReq toReq(){
            if (updateDate == null || updateTime == null) {
                throw new IllegalArgumentException("reservationDate and reservationTime must not be null");
            }
            LocalDate date = LocalDate.parse(updateDate);
            LocalTime time = LocalTime.parse(updateTime);
            //LocalDateTime reservationAt = LocalDateTime.of(date, time);

            return ProductReq.UpdateProdReq.builder()
                    .reservationDate(date)
                    .reservationTime(time)
                    .productProceedType(type)
                    .build();
        }
    }
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class DeleteInput{

        private String deleteDate;
        private String deleteTime;

        public ProductReq.DeleteProdReq toReq(){
            if (deleteDate == null || deleteTime == null) {
                throw new IllegalArgumentException("reservationDate and reservationTime must not be null");
            }
            LocalDate date = LocalDate.parse(deleteDate);
            LocalTime time = LocalTime.parse(deleteTime);
            //LocalDateTime reservationAt = LocalDateTime.of(date, time);

            return ProductReq.DeleteProdReq.builder()
                    .reservationDate(date)
                    .reservationTime(time)
                    .build();
        }
    }



}






