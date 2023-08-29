package com.devtalk.product.productservice.product.adapter.in.web;


import com.devtalk.product.productservice.product.adapter.in.web.dto.ProductInput;
import com.devtalk.product.productservice.product.adapter.in.web.dto.ProductOutput;
import com.devtalk.product.productservice.product.application.port.in.ProductUseCase;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq.ReserveProdReq;

import com.devtalk.product.productservice.product.application.port.in.dto.ProductRes;
import com.devtalk.product.productservice.product.domain.product.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@EnableDiscoveryClient
@RestController
@Slf4j
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor

public class ProductApiController {
    private final ProductUseCase productUseCase;

    //상품등록API - 완료
    //상담자가 상담 가능한 시간을 설정한다.
    @PostMapping("/consultant/{consultantid}/register")
    public ResponseEntity<?> registProduct(@RequestBody @Validated ProductInput.RegistrationInput registrationInput,
                                           @PathVariable Long consultantId) {
        productUseCase.registProduct(registrationInput.toReq(consultantId));
        return ResponseEntity.ok().build();
    }

    //상담사 예약 가능 상품 조회API - 완료
    //내담자가 상담을 원하는 상담자의 예약 가능 시간을 확인한다.
    @GetMapping("/search/consultant/{consultantid}")
    public ResponseEntity<ProductOutput> searchProductList(@PathVariable Long consultantId) {
        List<ProductRes.ConsultantProductListRes> consultantProductListRes = productUseCase.searchList(consultantId);
        ProductOutput productOutput
                = new ProductOutput("0500", "조회 성공", consultantProductListRes);
        return ResponseEntity.status(HttpStatus.OK).body(productOutput);
    }

    //상품 수정 - 완료
    //상담 진행 방식을 수정한다.
    @PutMapping("/api/products/update/{productId}")
    public ResponseEntity<?> updateProduct(@RequestBody @Validated ProductInput.UpdateInput updateInput,
                                           @PathVariable Long productId) {
        productUseCase.updateProductType(updateInput.toReq(productId));
        return ResponseEntity.ok().build();
    }

    //예약 상품 삭제 - 완료
    @DeleteMapping("delete/{consultationId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long consultationId) {
        productUseCase.deleteReservation(consultationId);
        return ResponseEntity.ok().build();
    }



    //마이페이지 예약 리스트 조회 - 완료
    @GetMapping("search/memberId/{memberId}")
    public ResponseEntity<ProductOutput> myConsultationList(@PathVariable Long memberId) {
        List<ProductRes.ReservedProductRes> myConsultatioinListRes = productUseCase.searchConsulationListByMemberId(memberId);
        ProductOutput productOutput
                = new ProductOutput("0500", "조회 성공", myConsultatioinListRes);
        return ResponseEntity.status(HttpStatus.OK).body(productOutput);
    }

//    //상품예약API - 완료
//    @PostMapping("/productId/{productId}/")
//    public ResponseEntity<?> reserveProduct(@RequestBody @Validated ProductInput.ReservationInput reservationInput,
//                                            @PathVariable Long productId)
//    {
//        productUseCase.reserveProduct(reservationInput.toReq(productId));
//        return ResponseEntity.ok().build();
//    }
//
//    //예약상품조회API - 완료
//    @GetMapping("search?consultation={consultationId}")
//    public ResponseEntity<?> searchProduct(@PathVariable Long consultationId)
//    {
//        productUseCase.searchReservedDetatils(consultationId);
//        return ResponseEntity.ok().build();
//    }
//
//
//
}