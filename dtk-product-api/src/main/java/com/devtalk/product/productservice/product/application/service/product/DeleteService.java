package com.devtalk.product.productservice.product.application.service.product;

import com.devtalk.product.productservice.global.error.ErrorCode;
import com.devtalk.product.productservice.global.error.exception.NotFoundException;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq;
import com.devtalk.product.productservice.product.application.port.in.product.DeleteUseCase;
import com.devtalk.product.productservice.product.application.port.out.repository.ProductQueryableRepo;
import com.devtalk.product.productservice.product.application.port.out.repository.ProductRepo;
import com.devtalk.product.productservice.product.domain.product.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteService implements DeleteUseCase {
    ProductQueryableRepo productQueryableRepo;
    ProductRepo productRepo;
    @Override
    @Transactional

    public void deleteProduct(Long consultantId, ProductReq.DeleteProdReq deleteProdReq) {
        Product product = productQueryableRepo.findByConsultantIdAndReservationAt(consultantId, deleteProdReq.getReservationAt())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_PRODUCT));
        productRepo.delete(product);

    }
}
