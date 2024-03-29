package com.devtalk.product.productservice.product.application.service.product;

import com.devtalk.product.productservice.global.error.ErrorCode;
import com.devtalk.product.productservice.global.error.exception.NotFoundException;
import com.devtalk.product.productservice.product.adapter.in.client.MemberServiceClient;
import com.devtalk.product.productservice.product.application.port.in.dto.MemberReq;
import com.devtalk.product.productservice.product.application.port.in.product.RegistUseCase;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq;
import com.devtalk.product.productservice.product.application.port.out.repository.ProductRepo;
import com.devtalk.product.productservice.product.domain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RegistService implements RegistUseCase {
    private final ProductRepo productRepo;
    private final MemberServiceClient memberServiceClient;

    @Transactional
    public void registProduct(Long consultantId, ProductReq.RegistProdReq registProdReq) {
        //todo 검증절차 필요한지 확인하기
        //foreign으로 member-service로 부터 member 정보 받아오기
        MemberReq.ConsultantReq consultantReq = findConsultantInfo(consultantId);
        MemberReq.ConsultantReq consultantReq2 = consultantReq;
        Product product = Product.registProduct(consultantReq.getConsultantId(),
                registProdReq.getReservationDate(),
                registProdReq.getReservationTime(),
                registProdReq.getProductProceedType(),
                consultantReq.getF2fCost(),
                consultantReq.getNf2fCost());
        productRepo.save(product);
    }

    public MemberReq.ConsultantReq findConsultantInfo(Long consultantId){
        return Optional.ofNullable(memberServiceClient.getConsultantInfo(consultantId))
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_CONSULTANT));
    }
}

