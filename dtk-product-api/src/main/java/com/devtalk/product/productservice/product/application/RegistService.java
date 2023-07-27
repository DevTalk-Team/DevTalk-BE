package com.devtalk.product.productservice.product.application;

import com.devtalk.product.productservice.product.application.port.in.RegistUseCase;
import com.devtalk.product.productservice.product.application.port.in.dto.RegistProdReq;
import com.devtalk.product.productservice.product.application.port.out.repository.MemberQueryableRepo;
import com.devtalk.product.productservice.product.application.port.out.repository.ProductRepo;
import com.devtalk.product.productservice.product.application.validator.Validator;
import com.devtalk.product.productservice.product.domain.member.Consultant;
import com.devtalk.product.productservice.global.error.exception.NotFoundException;
import static com.devtalk.product.productservice.global.error.ErrorCode.*;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RegistService implements RegistUseCase {
    private com.devtalk.product.productservice.product.domain.product.Product product;
    private Validator validator;
    private MemberQueryableRepo memberQueryableRepo;

    private ProductRepo productRepo;
    @Override
    public void registProduct(RegistProdReq registProdReq) {
        validator.validate(registProdReq);
        Consultant consultant = searchConsultant(registProdReq.getConsultantId());

        product.registProduct(consultant, registProdReq.getReservationAt(),
                registProdReq.getType(),
                registProdReq.getStatus());
        productRepo.save(product);
    }


    private Consultant searchConsultant(Long consultantId) {
        return memberQueryableRepo.findByConsultantId(consultantId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_CONSULTANT));
    }


}
