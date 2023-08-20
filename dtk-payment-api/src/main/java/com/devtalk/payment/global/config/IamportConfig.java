//package com.devtalk.payment.global.config;
//
//import com.devtalk.payment.paymentservice.application.PaymentProperty;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class IamportConfig {
//    @Value("${portone.imp_key}")
//    private String impKey;
//
//    @Value("${portone.imp_secret}")
//    private String impSecret;
//
//    public IamportConfig(
//            @Value("${portone.imp_key}") String impKey,
//            @Value("${portone.imp_secret}") String impSecret){
//        this.impKey = impKey;
//        this.impSecret = impSecret;
//    }
//
//    @Bean
//    public PaymentProperty init() {
//        return new PaymentProperty(impKey, impSecret);
//    }
//
//    public String getImpKey() {
//        return impKey;
//    }
//
//    public String getImpSecret() {
//        return impSecret;
//    }
//}