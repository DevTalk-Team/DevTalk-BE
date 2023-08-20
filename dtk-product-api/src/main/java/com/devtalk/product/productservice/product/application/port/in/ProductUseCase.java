package com.devtalk.product.productservice.product.application.port.in;

import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq;
import com.devtalk.product.productservice.product.domain.product.Product;
import com.devtalk.product.productservice.product.domain.product.ReservedProduct;

import java.util.List;

public interface ProductUseCase {
    void registProduct(ProductReq.RegistProdReq registProdReq);

    List<Product> searchList(Long consultantId);

    void reserveProduct(ProductReq.ReserveProdReq reserveProdReq);

  //  Product searchProduct(ProductReq.ReserveProdReq reserveProdReq);



    //void deleteReservation(ProductReq.deleteProdReq deleteProdReq);

//    ReservedProduct searchReservation(ProductReq productReq);중
}
