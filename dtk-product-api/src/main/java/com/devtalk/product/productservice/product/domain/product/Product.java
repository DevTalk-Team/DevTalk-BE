package com.devtalk.product.productservice.product.domain.product;

import com.devtalk.product.productservice.global.vo.BaseTime;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq;
//import com.devtalk.product.productservice.product.domain.member.Consultant;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.apachecommons.CommonsLog;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
public class Product extends BaseTime {
    //상품ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    //상담자ID
    @Column(name = "consultant_id")
    private Long consultantId;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "consultant_id", nullable = false)
//    private Consultant consultant;

    //상태
    @Column(name = "status", nullable = false)
    private String status;

    //상담시간
    @Column(name = "reservation_date", nullable = false)
    private LocalDate reservationDate;

    @Column(name = "reservation_time", nullable = false)
    private LocalTime reservationTime;

    //상담유형
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ProductProceedType productProceedType;

    @Column(name = "nf2f_cost")
    private Integer NF2FCost;

    @Column(name = "f2f_cost")
    private Integer F2FCost;


    public Product(Long consultantId, String status, LocalDate reservationDate, LocalTime reservationTime, ProductProceedType type
                   ,Integer F2FCost,Integer NF2FCost){
        this.consultantId=consultantId;
        this.status=status;
        this.reservationDate=reservationDate;
        this.reservationTime=reservationTime;
        this.productProceedType=type;
        this.F2FCost=F2FCost;
        this.NF2FCost=NF2FCost;
    }

//    public Product(Consultant consultant, String status, LocalDateTime reservationAt, ProductProceedType type){
//        this.consultant=consultant;
//        this.status=status;
//        this.reservationAt=reservationAt;
//        this.type=type;
//    }




    public static Product registProduct(Long consultantId, LocalDate reservationDate, LocalTime reservationTime, ProductProceedType productProceedType
    ,Integer F2FCost,Integer NF2FCost){
        return Product.builder()
                .consultantId(consultantId)
                .status("AVAILABLE")
                .reservationDate(reservationDate)
                .reservationTime(reservationTime)
                .productProceedType(productProceedType)
                .F2FCost(F2FCost)
                .NF2FCost(NF2FCost)
                .build();
    }

    public void updateProductType(ProductProceedType productProceedType){
        this.productProceedType = productProceedType;
    }

    public void reserveProduct(){

        this.status = "UNAVAILABLE";
    }


    public void cancleConsultation(){
        this.status = "AVAILABLE";
    }


//    public static Product registProduct(Consultant consultant, LocalDateTime reservationAt, ProductProceedType type){
//        return Product.builder()
//                .consultant(consultant)
//                .status("예약 가능")
//                .reservationAt(reservationAt)
//                .type(type)
//                .build();

//    }

//    public static Product deleteReservation(Long productId){
//        return Product.builder()
//                .id(productId)
//                .status("예약 가능")
//                .build();
//    }
//

}
