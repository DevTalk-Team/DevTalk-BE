package com.devtalk.payment.global.config.property;

import org.springframework.stereotype.Component;

@Component
public class PaymentProperty {
    private String impKey;
    private String impSecret;

    public PaymentProperty(String impKey, String impSecret) {
        this.impKey = impKey;
        this.impSecret = impSecret;
    }

    public String getImpKey() {
        return impKey;
    }

    public String getImpSecret() {
        return impSecret;
    }
}