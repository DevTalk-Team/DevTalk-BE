package com.devtalk.product.productservice.product.domain.product;

import com.devtalk.product.productservice.global.vo.BaseTime;
import com.devtalk.product.productservice.product.domain.member.Consultant;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Product extends BaseTime {
    //상품ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    //상담자ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_id", nullable = false)
    private Consultant consultant;

    //상태
    @Column(name = "status", nullable = false)
    private String status;

    //상담시간
    @Column(name = "reservation_at", nullable = false)
    private LocalDateTime reservationAt;

    //상담유형
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ConsultationType type;




    public static Product registProduct(Consultant consultant, LocalDateTime reservationAt, ConsultationType type){
        return Product.builder()
                .consultant(consultant)
                .status("예약 가능")
                .reservationAt(reservationAt)
                .type(type)
                .build();
    }

    public static Product deleteReservation(Long productId){
        return Product.builder()
                .status("예약 가능")
                .build();
    }


}
