package com.devtalk.product.productservice.product.application.port.in;

import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductRes;
import com.devtalk.product.productservice.product.domain.product.Product;


import java.util.List;

import static com.devtalk.product.productservice.product.application.port.in.dto.ProductReq.*;

public interface ProductUseCase {

    //상품 등록
    void registProduct(RegistProdReq registProdReq);

    //상담자 예약 가능 상품 조회
    List<ProductRes.ConsultantProductListRes> searchList(Long consultantId);

    //상품 수정
    void updateProductType(ProductReq.UpdateProdReq updateProdReq);


    //예약 상품 삭제
    void deleteReservation(Long consultationId);

    //마이페이지 예약 리스트 조회
    List<ProductRes.ReservedProductRes> searchConsulationListByMemberId (Long memberId);

//    void reserveProduct(ProductReq.ReserveProdReq reserveProdReq);

//    List<ProductRes.ReservedProductRes> searchReservedProductsByMember(Long memberId);
//
//
//    ProductRes.ReservedProductRes searchReservedDetatils(Long consultationId);
//
//


//    ReservedProduct searchReservation(ProductReq productReq);중
}
