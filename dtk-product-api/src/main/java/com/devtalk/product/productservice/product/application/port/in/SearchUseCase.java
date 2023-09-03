package com.devtalk.product.productservice.product.application.port.in;

import com.devtalk.product.productservice.product.application.port.in.dto.ProductRes;

import java.util.List;

public interface SearchUseCase {
    List<ProductRes.ConsultantProductListRes> searchList(Long consultantId);

    //마이페이지 예약 리스트 조회
    List<ProductRes.ReservedProductRes> searchConsulationListByMemberId (Long memberId);
}
