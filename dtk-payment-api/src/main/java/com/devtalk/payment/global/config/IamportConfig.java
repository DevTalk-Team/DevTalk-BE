package com.devtalk.payment.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IamportConfig {
    @Value("${portone.imp_key}")
    private String impKey;

    @Value("${portone.imp_secret}")
    private String impSecret;

//    public IamportConfig(
//            @Value("${portone.imp_key}") String impKey,
//            @Value("${portone.imp_secret}") String impSecret){
//        this.impKey = impKey;
//        this.impSecret = impSecret;
//    }

    public String getImpKey() {
        return impKey;
    }

    public String getImpSecret() {
        return impSecret;
    }
}
