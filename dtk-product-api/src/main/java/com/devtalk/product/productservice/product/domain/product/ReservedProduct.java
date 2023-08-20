package com.devtalk.product.productservice.product.domain.product;

import com.devtalk.product.productservice.product.domain.member.Consultant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReservedProduct extends Product {
    @Column(unique = true, nullable = false)
    private Long productId;

    @Column(name = "consulter")
    private Long consulter;

    @Column(name = "price")
    private int price;

    @Column(name = "area")
    private String area;
    public static ReservedProduct reserveProduct(Long productId, Consultant consultant, Long consulter,
                                                 LocalDateTime reservationAt, ConsultationType type){
        return ReservedProduct.builder()
                .productId(productId)
                .consultant(consultant)
                .consulter(consulter)
                .status("예약 중")
                .reservationAt(reservationAt)
                .type(type)
                .price(consultant.getPrice(type))
                .area(consultant.getArea())
                .build();
    }
}
