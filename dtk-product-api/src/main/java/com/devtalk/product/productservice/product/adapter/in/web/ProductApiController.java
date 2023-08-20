package com.devtalk.product.productservice.product.adapter.in.web;

import com.devtalk.product.productservice.product.adapter.in.web.dto.ProductInput;
import com.devtalk.product.productservice.product.application.port.in.ProductUseCase;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.devtalk.product.productservice.product.adapter.in.web.dto.ProductInput.*;


@EnableDiscoveryClient
@RestController
@Slf4j
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor

public class ProductApiController {
    private final ProductUseCase productUseCase;

    //상품등록API - 완료
    @PostMapping("/register")
    public ResponseEntity<?> registProduct(@RequestBody @Validated RegistrationInput registrationInput,
                                           @RequestParam Long loggedInUserId)
    {
        productUseCase.registProduct(registrationInput.toReq(loggedInUserId));
        return ResponseEntity.ok().build();
    }

    //상담사별상품조회API - 완료
    @GetMapping("search?consultantId={consultantId}")
    public ResponseEntity<?> searchList(@PathVariable Long consultantId)
    {
        productUseCase.searchList(consultantId);
        return ResponseEntity.ok().build();
    }

    //상품예약API - 진행 중
    @PostMapping("reservation/productId={productId}")
    public ResponseEntity<?> reserveProduct(@RequestBody @Validated ReservationInput reservationInput,
                                            @PathVariable Long productId)
    {
        productUseCase.reserveProduct(reservationInput.toReq(productId));
        return ResponseEntity.ok().build();
    }

    //상품조회API - 진행 중
//    @GetMapping("search?consultation={consultationid}")
//    public ResponseEntity<?> searchProduct(@PathVariable Long consultationId)
//    {
//        registUseCase.searchProduct(consultationId);
//        return ResponseEntity.ok().build();


//    }
    //상품삭제API
    @DeleteMapping("delete")
    public ResponseEntity<?> deleteProduct(@RequestParam Long consultationid)
    {
        registUseCase.deleteProduct(consultationid);
        return ResponseEntity.ok().build();
    }
}
