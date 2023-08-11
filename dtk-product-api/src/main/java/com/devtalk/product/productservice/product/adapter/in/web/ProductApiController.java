package com.devtalk.product.productservice.product.adapter.in.web;

import com.devtalk.product.productservice.product.adapter.in.web.dto.ProductInput;
import com.devtalk.product.productservice.product.application.port.in.ProductUseCase;
import com.devtalk.product.productservice.product.application.port.in.RegistUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api")
@RequiredArgsConstructor

public class ProductApiController {
    private final ProductUseCase registUseCase;

    //상품등록API
    @PostMapping("/v1/products/register")
    public ResponseEntity<?> registProduct(@RequestBody @Validated ProductInput.RegistrationInput registrationInput,
                                           @PathVariable Long consultantId)
    {
        registUseCase.registProduct(registrationInput.toReq(consultantId));

        return ResponseEntity.ok().build();
    }

    //상품조회API
    @GetMapping("/v1/products/search?consultation={consultationid}")
    public ResponseEntity<?> searchProduct(@PathVariable Long consultationid)
    {
        registUseCase.searchProduct(consultationid);
        return ResponseEntity.ok().build();
    }
    //상담사별상품조회API
    @GetMapping("/v1/products/search?consultant={consultant}")
    public ResponseEntity<?> searchList(@PathVariable Long consultant)
    {
        registUseCase.searchList(consultant);
        return ResponseEntity.ok().build();
    }

    //상품수정API
    @PutMapping("/v1/products/update")
    public ResponseEntity<?> updateProduct(@RequestBody @Validated RegistrationInput productInput,
                                           @PathVariable Long consultantId)
    {
        registUseCase.updateProduct(UpdateInput.toReq(consultantId));
        return ResponseEntity.ok().build();
    }
    //상품삭제API
    @DeleteMapping("/v1/products/register")
    public ResponseEntity<?> deleteProduct(@RequestParam Long consultationid)
    {
        registUseCase.deleteProduct(consultationid);
        return ResponseEntity.ok().build();
    }
}
