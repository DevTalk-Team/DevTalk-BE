package com.devtalk.product.productservice.product.application.service.product;

import com.devtalk.product.productservice.global.error.exception.NotFoundException;
import com.devtalk.product.productservice.product.application.port.in.product.UpdateUseCase;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq;
import com.devtalk.product.productservice.product.application.port.out.repository.ProductQueryableRepo;
import com.devtalk.product.productservice.product.application.port.out.repository.ProductRepo;
import com.devtalk.product.productservice.product.domain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.devtalk.product.productservice.global.error.ErrorCode.NOT_FOUND_PRODUCT;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UpdateService implements UpdateUseCase {
    private final ProductRepo productRepo;
    private final ProductQueryableRepo productQueryableRepo;

    @Transactional
    public void updateProductType(Long consultantId, ProductReq.UpdateProdReq updateProdReq){
        Product product = productQueryableRepo.findByConsultantIdAndReservationAt(consultantId, updateProdReq.getReservationAt())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_PRODUCT));
        product.updateProductType(updateProdReq.getProductProceedType());
    }

}
