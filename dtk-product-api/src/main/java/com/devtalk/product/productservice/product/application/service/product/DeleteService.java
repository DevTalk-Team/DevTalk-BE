package com.devtalk.product.productservice.product.application.service.product;

import com.devtalk.product.productservice.global.error.exception.NotFoundException;
import com.devtalk.product.productservice.product.application.port.in.product.DeleteUseCase;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductRes;
import com.devtalk.product.productservice.product.application.port.out.repository.ProductRepo;
import com.devtalk.product.productservice.product.application.port.out.repository.ReservedProductRepo;
import com.devtalk.product.productservice.product.domain.product.Product;
import com.devtalk.product.productservice.product.domain.product.ProductReservedDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.devtalk.product.productservice.global.error.ErrorCode.NOT_FOUND_CONSULTANT;
import static com.devtalk.product.productservice.global.error.ErrorCode.NOT_FOUND_RESERVED_DETAILS;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeleteService implements DeleteUseCase {
    private final ProductRepo productRepo;
    private final ReservedProductRepo reservedProductRepo;

    //1.예약 상품 삭제
    @Transactional
    public void deleteReservation(Long consultationId) {
        ProductRes.ReservedProductRes reservedDetails = searchReservedDetatils(consultationId);
        Product product = searchProduct(reservedDetails.getProductId());
        reservedProductRepo.deleteById(reservedDetails.getConsultationId());
        product.deleteReservation(product.getId());
        productRepo.save(product);
        return;
    }

    //2.DB에서 예약 세부사항 조회
    @Transactional
    public ProductRes.ReservedProductRes searchReservedDetatils(Long reservationId) {
        ProductReservedDetails productReservedDetails = reservedProductRepo.findById(reservationId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_RESERVED_DETAILS));

        ProductRes.ReservedProductRes reservedProductInfo = ProductRes.loadInfo(productReservedDetails);
        return reservedProductInfo;
    }

    @Transactional
    public Product searchProduct(Long productId) {
        return productRepo.findById(productId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_CONSULTANT));
    }

}
