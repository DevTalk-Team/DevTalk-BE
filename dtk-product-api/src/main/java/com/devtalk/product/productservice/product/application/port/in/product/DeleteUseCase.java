package com.devtalk.product.productservice.product.application.port.in.product;

import com.devtalk.product.productservice.product.adapter.in.web.dto.ProductInput;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq;

public interface DeleteUseCase {
    void deleteProduct(Long consultantId, ProductReq.DeleteProdReq deleteProdReq);

}
