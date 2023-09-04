package com.devtalk.product.productservice.product.application.port.in.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TestDto {
    private String testStr;

    public TestDto(String testStr) {
        this.testStr = testStr;
    }
}