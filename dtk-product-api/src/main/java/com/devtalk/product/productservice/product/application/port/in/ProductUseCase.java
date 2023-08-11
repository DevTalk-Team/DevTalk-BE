package com.devtalk.product.productservice.product.application.port.in;

import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq;
import com.devtalk.product.productservice.product.domain.product.Product;

import java.util.List;

public interface ProductUseCase {
    void registProduct(ProductReq.RegistProdReq registProdReq);

    Product searchProduct(Long consultationid);

    void deleteProduct(Long consultationid);

    List<Product> searchList(Long consultant);


    void updateProduct(ProductReq.UpdateProdReq updateProdReq);
}
