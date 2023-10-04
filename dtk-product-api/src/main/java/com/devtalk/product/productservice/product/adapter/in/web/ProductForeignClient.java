package com.devtalk.product.productservice.product.adapter.in.web;

import com.devtalk.product.productservice.product.application.port.in.product.ProductInfoUseCase;
import com.devtalk.product.productservice.product.application.port.in.product.RegistUseCase;
import com.devtalk.product.productservice.product.application.port.in.product.SearchUseCase;
import com.devtalk.product.productservice.product.application.port.in.product.UpdateUseCase;
import com.devtalk.product.productservice.product.application.port.out.dto.ProductInfoRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@RestController
@Slf4j
@RequestMapping("/product/foreign")
@RequiredArgsConstructor

 class ProductForeignClient {
    private final ProductInfoUseCase productInfoUseCase;

    private final Environment env;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Product Service"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")
                + ", port(server.port)=" + env.getProperty("server.port")
                + ", token secret=" + env.getProperty("token.secret")
                + ", token expiration time=" + env.getProperty("token.expiration_time"));
    }

    @GetMapping("/products/info/{productId}")
    public ProductInfoRes.ProductSearchRes getProduct(@PathVariable Long productId){
       return productInfoUseCase.findProduct(productId);
    }
}
