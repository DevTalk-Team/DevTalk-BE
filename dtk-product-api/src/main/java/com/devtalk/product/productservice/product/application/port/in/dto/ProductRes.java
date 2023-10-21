package com.devtalk.product.productservice.product.application.port.in.dto;

import com.devtalk.product.productservice.product.domain.product.Product;
import com.devtalk.product.productservice.product.domain.product.ProductProceedType;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter

public class ProductRes {
    @Getter
    @Builder
    public static class ConsultantProductListRes {
        private Long productId;
        private Long consultantId;
        private String status;
        private LocalDateTime reservationAt;
        private ProductProceedType productProceedType;
    }

    @Getter
    @Builder
    public static class ProductDetailsRes {
        private Long productId;
        private Long consultantId;
        private String status;
        private LocalDateTime reservationAt;
        private ProductProceedType productProceedType;

        public static ProductDetailsRes of(Product product){
            return ProductDetailsRes.builder()
                    .productId(product.getId())
                    .consultantId(product.getConsultantId())
                    .status(product.getStatus())
                    .reservationAt(product.getReservationAt())
                    .productProceedType(product.getProductProceedType())
                    .build();
        }
    }
}



//    @Getter
//    @Builder
//    public static class ReservedProductRes {
//        private Long productId;
//        private Long consultationId;
//        private Long consultantId;
//        private Long consulterId;
//        private String status;
//        private LocalDateTime reservationAt;
//        private Long price;
//        private ReservedProceedType reservedProceedType;
//        private String region;
//    }

//    public static ReservedProductRes loadInfo(ProductReservedDetails productReservedDetails){
//        return ReservedProductRes.builder()
//                .productId(productReservedDetails.getProduct().getId())
//                .consultationId(productReservedDetails.getId())
//                .consultantId(productReservedDetails.getProduct().getConsultant().getId())
//                .consulterId(productReservedDetails.getConsulterId())
//                .status(productReservedDetails.getStatus())
//                .reservationAt(productReservedDetails.getProduct().getReservationAt())
//                .price(productReservedDetails.getPrice())
//                .reservedProceedType(productReservedDetails.getReservedProceedType())
//                .region(productReservedDetails.getRegion())
//                .build();
//    }
//}
