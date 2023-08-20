package com.devtalk.product.productservice.product.application;

import com.devtalk.product.productservice.product.application.port.in.ProductUseCase;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq;

import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq.RegistProdReq;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq.ReserveProdReq;
import com.devtalk.product.productservice.product.application.port.out.repository.ConsultantQueryableRepo;
import com.devtalk.product.productservice.product.application.port.out.repository.ProductRepo;
import com.devtalk.product.productservice.product.application.port.out.repository.ReservedProductRepo;
import com.devtalk.product.productservice.product.application.validator.Validator;
import com.devtalk.product.productservice.global.error.exception.NotFoundException;
import static com.devtalk.product.productservice.global.error.ErrorCode.*;


import com.devtalk.product.productservice.product.domain.member.Consultant;
import com.devtalk.product.productservice.product.domain.product.Product;
import com.devtalk.product.productservice.product.domain.product.ReservedProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {
    private final Validator validator;
    private final ConsultantQueryableRepo consultantQueryableRepo;
    private final ProductRepo productRepo;
    private final ReservedProductRepo reservedProductRepo;

    @Transactional
    public void registProduct(RegistProdReq registProdReq) {
        validator.validate(registProdReq);
        Consultant consultant = searchConsultant(registProdReq.getConsultantId());
        Product product = Product.registProduct(consultant, registProdReq.getReservationAt(),
                registProdReq.getType());
        productRepo.save(product);
    }

    @Transactional
    public List<Product> searchList(Long consultantId) {
        validator.validate(consultantId);
        return productRepo.findAllByConsultantId(consultantId);
    }

    @Transactional
    public void reserveProduct(ReserveProdReq reserveProdReq) {
        Consultant consultant = searchConsultant(reserveProdReq.getConsultantId());

        Product product = Product.unavailableProduct(consultant, reserveProdReq.getReservationAt(),
                reserveProdReq.getType());
        ReservedProduct reservedProduct = ReservedProduct.reserveProduct(reserveProdReq.getProductId(), consultant,
                reserveProdReq.getConsulterId(), reserveProdReq.getReservationAt(),
                reserveProdReq.getType());
        productRepo.save(product);
        reservedProductRepo.save(reservedProduct);


    }
//    @Transactional
//    public Product searchProduct(Long productId) {
//        return productRepo.findById(productId)
//                .orElseThrow(() -> new NotFoundException(NOT_FOUND_CONSULTANT));
//    }

//    @Override
//    @Transactional
//    public void deleteProduct(Long productId) {
//        productRepo.deleteById(productId);
//    }





    private Consultant searchConsultant(Long consultantId) {
        return consultantQueryableRepo.findByid(consultantId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_CONSULTANT));
    }
}
