package com.devtalk.product.productservice.product.application.port.in.product;

import com.devtalk.product.productservice.product.application.port.in.dto.ProductRes;

import java.util.List;

public interface SearchUseCase {
    //상담자 예약 가능 상품 조회
    List<ProductRes.ConsultantProductListRes> searchList(Long consultantId);

    //마이페이지 예약 리스트 조회
    List<ProductRes.ReservedProductRes> searchConsulationListByMemberId (Long memberId);
}
