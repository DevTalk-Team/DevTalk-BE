package com.devtalk.product.productservice.product.application.service.product;

import com.devtalk.product.productservice.product.adapter.in.client.MemberServiceClient;
import com.devtalk.product.productservice.product.application.port.in.dto.MemberReq;
import com.devtalk.product.productservice.product.application.port.in.product.RegistUseCase;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq;
import com.devtalk.product.productservice.product.application.port.out.repository.ProductRepo;
import com.devtalk.product.productservice.product.domain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RegistService implements RegistUseCase {
    private final ProductRepo productRepo;
    private final MemberServiceClient memberServiceClient;

    @Transactional
    public void registProduct(ProductReq.RegistProdReq registProdReq) {
        //todo 검증절차 필요한지 확인하기
        //foreign으로 member-service로 부터 member 정보 받아오기
        MemberReq.ConsultantPriceReq consultantPriceReq = memberServiceClient.getPrice(registProdReq.getConsultantId());
        Product product = Product.registProduct(registProdReq.getConsultantId(),
                registProdReq.getReservationAt(),
                registProdReq.getProductProceedType(),
                consultantPriceReq.getF2F_Price(),
                consultantPriceReq.getNF2F_Price());
        productRepo.save(product);
    }
}

