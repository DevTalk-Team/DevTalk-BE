package com.devtalk.product.productservice.product.application;

import com.devtalk.product.productservice.product.application.port.in.ProductUseCase;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq;

import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq.RegistProdReq;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq.UpdateProdReq;
import com.devtalk.product.productservice.product.application.port.out.repository.ConsultantQueryableRepo;
import com.devtalk.product.productservice.product.application.port.out.repository.ProductRepo;
import com.devtalk.product.productservice.product.application.validator.Validator;
import com.devtalk.product.productservice.global.error.exception.NotFoundException;
import static com.devtalk.product.productservice.global.error.ErrorCode.*;


import com.devtalk.product.productservice.product.domain.member.Consultant;
import com.devtalk.product.productservice.product.domain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {
    private final com.devtalk.product.productservice.product.domain.product.Product product;
    private final Validator validator;
    private final ConsultantQueryableRepo consultantQueryableRepo;

    private final ProductRepo productRepo;

    //상품등록
    @Transactional
    public void registProduct(RegistProdReq registProdReq) {
        validator.validate(registProdReq);
        Consultant consultant = searchConsultant(registProdReq.getConsultantId());

        product.registProduct(consultant, registProdReq.getReservationAt(), registProdReq.getType()); // Corrected method calls
        productRepo.save(product);
    }

    //상품조회
    @Transactional
    public Product searchProduct(Long consultationid) {
        return productRepo.findAllById(consultationid);
    }

    @Override
    public void deleteProduct(Long consultationid) {
        productRepo.deleteById(consultationid);
        return;
    }

    @Override
    public List<Product> searchList(Long consultant) {
        productRepo.findAllByConsultantId(consultant);
        return null;
    }


    @Transactional
        public void updateProduct(ProductReq updateProdReq) {

        Consultant consultant = searchConsultant(updateProdReq.getConsultantId());

        product.registProduct(consultant, updateProdReq.getReservationAt(), updateProdReq.getType()); // Corrected method calls
        productRepo.save(product);
    }


    private Consultant searchConsultant(Long consultantId) {
        return consultantQueryableRepo.findByConsultantId(consultantId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_CONSULTANT));
    }


}
