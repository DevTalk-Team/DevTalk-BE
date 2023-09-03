package com.devtalk.product.productservice.product.application.port.in;

import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq;

public interface RegistUseCase {
    //상품 등록
    void registProduct(ProductReq.RegistProdReq registProdReq);
}
