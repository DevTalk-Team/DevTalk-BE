package com.devtalk.product.productservice.product.application.port.in.product;

import com.devtalk.product.productservice.global.error.exception.NotFoundException;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductRes;
import com.devtalk.product.productservice.product.application.port.out.repository.ProductRepo;
import com.devtalk.product.productservice.product.domain.product.Product;
import com.devtalk.product.productservice.product.domain.product.ProductReservedDetails;
import org.springframework.transaction.annotation.Transactional;

import static com.devtalk.product.productservice.global.error.ErrorCode.NOT_FOUND_CONSULTANT;
import static com.devtalk.product.productservice.global.error.ErrorCode.NOT_FOUND_RESERVED_DETAILS;

public interface DeleteUseCase {
    public void deleteReservation(Long consultationId);
}
