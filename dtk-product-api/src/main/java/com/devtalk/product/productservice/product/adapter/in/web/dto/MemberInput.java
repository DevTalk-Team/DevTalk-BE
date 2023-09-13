//package com.devtalk.product.productservice.product.adapter.in.web.dto;
//
//import com.devtalk.product.productservice.product.application.port.in.dto.ConsultantReq;
//import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq;
//import com.devtalk.product.productservice.product.domain.member.MemberType;
//import com.devtalk.product.productservice.product.domain.product.ProductProceedType;
//import lombok.*;
//
//import java.time.LocalDateTime;
//
//public class MemberInput {
//    @Builder
//    @AllArgsConstructor(access = AccessLevel.PRIVATE)
//    @NoArgsConstructor(access = AccessLevel.PRIVATE)
//    @Getter
//    public static class ConsultantInput {
//
//        private Long id;
//        private String name;
//        private MemberType memberType;
//        private String phoneNumber;
//        private Long nf2f_Price;
//
//        private Long f2f_Price;
//        private String region;
//
//
//        public ConsultantReq toReq() {
//            return ConsultantReq.builder()
//                    .phoneNumber(phoneNumber)
//                    .memberType(memberType)
//                    .name(name)
//                    .nf2f_Price(nf2f_Price)
//                    .f2f_Price(f2f_Price)
//                    .region(region)
//                    .build();
//        }
//    }
//}