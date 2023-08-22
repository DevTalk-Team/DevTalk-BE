package com.devtalk.payment.paymentservice.application;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class PaymentProperty {

    @Value("${portone.imp_key}")
    private String impKey;

    @Value("${portone.imp_secret}")
    private String impSecret;

    public PaymentProperty(
            @Value("${portone.imp_key}") String fileListMaxSize,
            @Value("{portone.imp_secret}") String fileListMaxCount) {
        this.impKey = fileListMaxSize;
        this.impSecret = fileListMaxCount;
    }


    public String getImpKey() {
        return impKey;
    }

    public String getImpSecret() {
        return impSecret;
    }
}