package com.devtalk.product.productservice.product.domain.product;

import com.devtalk.product.productservice.product.domain.member.Consultant;
import com.devtalk.product.productservice.product.domain.member.Consulter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class ReservedDetails extends Product {

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

    @Column(name = "ReservedType")
    @Enumerated(EnumType.STRING)
    private ReservedType reservedType;

    @Column(name = "area")
    private String area;

    public static ReservedDetails reserveProduct(Product product, Consultant consultant, Consulter consulter,
                                                 ReservedType reservedType) {
        return ReservedDetails.builder()
                .product(product)
                .consulterId(consulter.getId())
                .price(consultant.getPrice(reservedType))
                .reservedType(reservedType)
                .area(consultant.getArea())
                .build();
    }
}
