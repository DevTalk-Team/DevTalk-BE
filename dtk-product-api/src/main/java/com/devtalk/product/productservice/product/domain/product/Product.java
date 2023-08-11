package com.devtalk.product.productservice.product.domain.product;

import com.devtalk.product.productservice.product.domain.member.Consultant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    //상품ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    //상담자ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_id")
    private Consultant consultant;

    //상태
    @Column(name = "status")
    private String status;



    //상담시간
    @Column(name = "reservation_at")
    private LocalDateTime reservationAt;

    //상담유형
    @Column(name = "type")
    private String type;

    //상담 지역
    @Column(name = "area")
    private String area;

    //가격
    @Column(name = "price")
    private int price;

    public static Product registProduct(Consultant consultant, LocalDateTime reservationAt, String type){
        return Product.builder()
                .consultant(consultant)
                .status("예약 대기")
                .reservationAt(reservationAt)
                .type(type)
                .area(consultant.getArea())
                .price(0)
                .build();
    }

    public static Product reserveProduct(Consultant consultant, String status, LocalDateTime reservationAt, String type, String area, int price){
        return Product.builder()
                .consultant(consultant)
                .status(status)
                .reservationAt(reservationAt)
                .type(type)
                .area(consultant.getArea())
                .price(consultant.getPrice(type))
                .build();
    }

    public static Product searchProduct(Long consultationid) {
        return null;
    }
}
