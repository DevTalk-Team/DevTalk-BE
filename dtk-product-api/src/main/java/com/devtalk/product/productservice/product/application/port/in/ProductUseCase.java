package com.devtalk.product.productservice.product.application.port.in;

import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductRes;
import com.devtalk.product.productservice.product.domain.product.Product;


import java.util.List;

import static com.devtalk.product.productservice.product.application.port.in.dto.ProductReq.*;

public interface ProductUseCase {
    void registProduct(RegistProdReq registProdReq);

    List<Product> searchList(Long consultantId);

    void reserveProduct(ProductReq.ReserveProdReq reserveProdReq);

    List<ProductRes.ReservedProductRes> searchReservedProductsByMember(Long memberId);


    ProductRes.ReservedProductRes searchReservedDetatils(Long consultationId);


    void deleteReservation(Long consultationId);

//    ReservedProduct searchReservation(ProductReq productReq);중
}
