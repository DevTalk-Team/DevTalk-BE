package com.devtalk.product.productservice.product.application.port.in;

import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq;

public interface UpdateUseCase {
    public void updateProductType(ProductReq.UpdateProdReq updateProdReq);

}
