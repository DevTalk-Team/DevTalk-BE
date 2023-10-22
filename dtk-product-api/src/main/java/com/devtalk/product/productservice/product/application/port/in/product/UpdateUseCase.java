package com.devtalk.product.productservice.product.application.port.in.product;

import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq;

public interface UpdateUseCase {
    public void updateProductType(Long consultantId, ProductReq.UpdateProdReq updateProdReq);

}
