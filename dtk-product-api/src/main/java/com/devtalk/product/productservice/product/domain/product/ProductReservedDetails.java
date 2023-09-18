//package com.devtalk.product.productservice.product.domain.product;
//
//import jakarta.persistence.*;
//import lombok.*;
//import lombok.experimental.SuperBuilder;
//
//@Entity
//@Table
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Getter
//@SuperBuilder
//public class ProductReservedDetails extends Product {
//
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ReservedInfo_id")
//    private Long id;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id")
//    private Product product;
//
//    @Column(name = "Consulter")
//    private Long consulterId;
//
//    @Column(name = "price")
//    private Long price;
//
//    @Column(name = "ReservedProceedType")
//    @Enumerated(EnumType.STRING)
//    private ReservedProceedType reservedProceedType;
//
//    @Column(name = "region")
//    private String region;
//
////    @Builder(builderMethodName = "reservedDetailsBuilder")
////    public ProductReservedDetails(Product product, Long consulterId, int price,
////                                  ReservedProceedType reservedProceedType, String region) {
////        super(product.getConsultant(), product.getStatus(), product.getReservationAt(), product.getType());
////        this.product = product;
////        this.consulterId = consulterId;
////        this.price = price;
////        this.reservedProceedType = reservedProceedType;
////        this.region = region;
////    }
////    public static ProductReservedDetails reserveProduct(Product product, Consultant consultant,
////                                                        Consulter consulter, ReservedProceedType reservedProceedType) {
////        return ProductReservedDetails.reservedDetailsBuilder()
////                .product(product)
////                .consulterId(consulter.getId())
////                .price(consultant.getPrice(reservedProceedType))
////                .reservedProceedType(reservedProceedType)
////                .area(consultant.get())
////                .build();
////    }
//}
