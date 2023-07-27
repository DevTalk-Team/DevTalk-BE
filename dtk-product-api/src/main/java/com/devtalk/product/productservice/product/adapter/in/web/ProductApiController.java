package com.devtalk.product.productservice.product.adapter.in.web;

import com.devtalk.product.productservice.product.adapter.in.web.dto.ProductInput;
import com.devtalk.product.productservice.product.application.port.in.RegistUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.devtalk.product.productservice.product.adapter.in.web.dto.ProductInput.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor

public class ProductApiController {
    private final RegistUseCase registUseCase;

    @PostMapping("/v1/consultant/{consultantId}/product")
    public ResponseEntity<?> registProduct(@RequestBody @Validated RegistrationInput productInput,
                                           @PathVariable Long consultantId){
        registUseCase.registProduct(productInput.toReq(consultantId));
        return ResponseEntity.ok().build();
    }
}
