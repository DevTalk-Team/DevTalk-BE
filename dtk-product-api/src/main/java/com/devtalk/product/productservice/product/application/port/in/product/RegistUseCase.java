package com.devtalk.product.productservice.product.application.port.in.product;

import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq;

public interface RegistUseCase {
    //상품 등록
    void registProduct(Long consultantId, ProductReq.RegistProdReq registProdReq);
}
