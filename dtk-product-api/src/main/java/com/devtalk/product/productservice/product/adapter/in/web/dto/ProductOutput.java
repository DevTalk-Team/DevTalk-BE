package com.devtalk.product.productservice.product.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProductOutput<T> {
    private String code;
    private String message;
    private T result;
}
