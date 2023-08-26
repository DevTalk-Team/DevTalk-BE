package com.devtalk.product.productservice.product.adapter.in.web;


import com.devtalk.product.productservice.product.adapter.in.web.dto.ProductInput;
import com.devtalk.product.productservice.product.application.port.in.ProductUseCase;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq.ReserveProdReq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;




@EnableDiscoveryClient
@RestController
@Slf4j
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor

public class ProductApiController {
    private final ProductUseCase productUseCase;

    //상품등록API - 완료
    @PostMapping("/consultant/{consultantid}/register")
    public ResponseEntity<?> registProduct(@RequestBody @Validated ProductInput.RegistrationInput registrationInput,
                                           @PathVariable Long consultantid)
    {
        productUseCase.registProduct(registrationInput.toReq(consultantid));
        return ResponseEntity.ok().build();
    }

    //상담사별상품조회API - 완료
    //상담사의 이름(ID)를 입력하면 해당 상담사의 상담설정한 리스트가 나옴
    @GetMapping("search?consultantId={consultantId}")
    public ResponseEntity<?> searchList(@PathVariable Long consultantId)
    {
        productUseCase.searchList(consultantId);
        return ResponseEntity.ok().build();
    }

    //예약상품리스트조회API - 진행 중
    @GetMapping("search/memberId/{memberId}")
    public ResponseEntity<?> reservationList(@PathVariable Long memberId)
    {
        productUseCase.searchReservedProductsByMember(memberId);
        return ResponseEntity.ok().build();
    }
    //상품예약API - 완료
    @PostMapping("/productId/{productId}/")
    public ResponseEntity<?> reserveProduct(@RequestBody @Validated ProductInput.ReservationInput reservationInput,
                                            @PathVariable Long productId)
    {
        productUseCase.reserveProduct(reservationInput.toReq(productId));
        return ResponseEntity.ok().build();
    }

    //예약상품조회API - 완료
    @GetMapping("search?consultation={consultationId}")
    public ResponseEntity<?> searchProduct(@PathVariable Long consultationId)
    {
        productUseCase.searchReservedDetatils(consultationId);
        return ResponseEntity.ok().build();
    }

    //상품삭제API - 완료
    @DeleteMapping("delete/consultation/{consultationId}")
    public ResponseEntity<?> deleteProduct(@RequestParam Long consultationId)
    {
        productUseCase.deleteReservation(consultationId);
        return ResponseEntity.ok().build();
    }
}
