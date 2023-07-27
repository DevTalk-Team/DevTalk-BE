package com.devtalk.product.productservice.product.domain.product;

import com.devtalk.product.productservice.product.domain.member.Consultant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

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

    //상담시간
    @Column(name = "reservation_at")
    private LocalDateTime reservationAt;

    //상담유형
    @Column(name = "type")
    private int type;

    //가격
    @Column(name = "price")
    private int price;

    //카테고리
    @Column(name = "category")
    private String category;

    //상태
    @Column(name = "status")
    private String status;

    //매칭ID
    @Column(name = "matching_id")
    private Long matchingId;

    public static Product registProduct(Consultant consultant,
                                        LocalDateTime reservationAt, int type,
                                        String status){
        return Product.builder()
                .consultant(consultant)
                .reservationAt(reservationAt)
                .type(type)
                .status(status)
                .build();
    }
}
