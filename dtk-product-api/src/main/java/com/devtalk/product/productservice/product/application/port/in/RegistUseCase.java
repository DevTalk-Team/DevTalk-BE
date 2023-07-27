package com.devtalk.product.productservice.product.application.port.in;

import com.devtalk.product.productservice.product.application.port.in.dto.RegistProdReq;

public interface RegistUseCase {
    void registProduct(RegistProdReq registProdReq);
}
