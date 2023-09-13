package com.devtalk.product.productservice.product.application.service.product;

import com.devtalk.product.productservice.global.error.exception.NotFoundException;
//import com.devtalk.product.productservice.product.adapter.out.web.persistence.MemberQueryRepo;
import com.devtalk.product.productservice.product.application.port.in.product.RegistUseCase;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq;
import com.devtalk.product.productservice.product.application.port.out.repository.ProductRepo;
//import com.devtalk.product.productservice.product.domain.member.Consultant;
import com.devtalk.product.productservice.product.domain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.devtalk.product.productservice.global.error.ErrorCode.NOT_FOUND_CONSULTANT;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RegistService implements RegistUseCase {
    private final ProductRepo productRepo;
  //  private final MemberQueryRepo memberQueryRepo;
    @Transactional
    public void registProduct(ProductReq.RegistProdReq registProdReq) {
        //todo 검증절차 필요한지 확인하기
        //foreign으로 member-service로 부터 member 정보 받아오기
        //Consultant consultant = searchConsultant(registProdReq.getMemberId());
        Product product = Product.registProduct(registProdReq.getConsultantId(),
                registProdReq.getReservationAt(),
                registProdReq.getProductProceedType());
        productRepo.save(product);
    }

//    @Transactional
//    public Consultant searchConsultant(Long consultantId) {
//        return memberQueryRepo.findByConsultantId(consultantId)
//                .orElseThrow(() -> new NotFoundException(NOT_FOUND_CONSULTANT));
//    }
}
