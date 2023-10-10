package com.devtalk.product.productservice.product.application.port.in.product;

import com.devtalk.product.productservice.product.application.port.out.dto.ProductInfoRes;

public interface ProductInfoUseCase {
    ProductInfoRes.ProductSearchRes findProduct(Long productId);

}
