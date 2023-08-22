package com.devtalk.consultation.consultationservice.consultation.application.port.out.client;

import com.devtalk.consultation.consultationservice.consultation.application.port.out.client.dto.ProductRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.devtalk.consultation.consultationservice.consultation.application.port.out.client.dto.ProductRes.*;

@FeignClient(name = "product-service") // Discovery Server에 등록된 서비스 이름
public interface ProductServiceClient {

    @GetMapping("/products/{productId}")
    ProductSearchRes getProduct(@PathVariable Long productId);

}
