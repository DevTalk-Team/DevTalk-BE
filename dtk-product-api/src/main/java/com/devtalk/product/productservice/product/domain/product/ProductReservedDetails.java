package com.devtalk.product.productservice.product.domain.product;

import com.devtalk.product.productservice.product.domain.member.Consultant;
import com.devtalk.product.productservice.product.domain.member.Consulter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter

public class ProductReservedDetails extends Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReservedInfo_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "Consulter")
    private Long consulterId;

    @Column(name = "price")
    private int price;

    @Column(name = "ReservedProceedType")
    @Enumerated(EnumType.STRING)
    private ReservedProceedType reservedProceedType;

    @Column(name = "area")
    private String area;

    @Builder(builderMethodName = "reservedDetailsBuilder")
    public ProductReservedDetails(Product product, Long consulterId, int price,
                                  ReservedProceedType reservedProceedType, String area) {
        super(product.getConsultant(), product.getStatus(), product.getReservationAt(), product.getType());
        this.product = product;
        this.consulterId = consulterId;
        this.price = price;
        this.reservedProceedType = reservedProceedType;
        this.area = area;
    }
    public static ProductReservedDetails reserveProduct(Product product, Consultant consultant,
                                                        Consulter consulter, ReservedProceedType reservedProceedType) {
        return ProductReservedDetails.reservedDetailsBuilder()
                .product(product)
                .consulterId(consulter.getId())
                .price(consultant.getPrice(reservedProceedType))
                .reservedProceedType(reservedProceedType)
                .area(consultant.getArea())
                .build();
    }
}
