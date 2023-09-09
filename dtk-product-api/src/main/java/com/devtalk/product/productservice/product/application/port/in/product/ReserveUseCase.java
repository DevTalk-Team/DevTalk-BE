package com.devtalk.product.productservice.product.application.port.in.product;

import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductReservedDetailsReq;

public interface ReserveUseCase {
    //상품 등록
    void reserveProduct(ProductReservedDetailsReq productReservedDetailsReq);
}
