package com.devtalk.product.productservice.product.application.service.product;

import com.devtalk.product.productservice.global.error.ErrorCode;
import com.devtalk.product.productservice.product.application.port.in.product.ProductInfoUseCase;
import com.devtalk.product.productservice.product.application.port.out.dto.ProductInfoRes;
import com.devtalk.product.productservice.product.application.port.out.repository.ProductRepo;
import com.devtalk.product.productservice.product.domain.product.Product;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductInfoService implements ProductInfoUseCase {

    private final ProductRepo productRepo;

    @Override
    public ProductInfoRes.ProductSearchRes findProduct(Long productId){
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new NotFoundException(String.valueOf(ErrorCode.NOT_FOUND_PRODUCT)));
        return ProductInfoRes.ProductSearchRes.of(product);
    }
}
