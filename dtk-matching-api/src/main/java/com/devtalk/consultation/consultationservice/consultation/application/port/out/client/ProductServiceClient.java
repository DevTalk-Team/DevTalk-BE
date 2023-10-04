package com.devtalk.consultation.consultationservice.consultation.application.port.out.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import static com.devtalk.consultation.consultationservice.consultation.application.port.out.client.dto.ProductRes.*;

@FeignClient(name = "product-service") // Discovery Server에 등록된 서비스 이름
public interface ProductServiceClient {
    @GetMapping("/products/info/{productId}")
    ProductSearchRes getProduct(@PathVariable Long productId);

    @PatchMapping("/products/{productId}/accept")
    void acceptProduct(@PathVariable Long productId);

    @DeleteMapping("/products/{productId}")
    void cancelProduct(@PathVariable Long productId);
}
