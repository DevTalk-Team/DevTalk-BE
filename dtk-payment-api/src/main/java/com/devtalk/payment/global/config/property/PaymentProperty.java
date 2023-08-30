package com.devtalk.payment.global.config.property;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class PaymentProperty {

    @Value("${portone.imp_key}")
    private String impKey;

    @Value("${portone.imp_secret}")
    private String impSecret;

    @Value("${portone.imp_uid}")
    private String impUid;

    public PaymentProperty(
            @Value("${portone.imp_key}") String impKey,
            @Value("${portone.imp_secret}") String impSecret,
            @Value("${portone.imp_uid}") String impUid) {
        this.impKey = impKey;
        this.impSecret = impSecret;
        this.impUid = impUid;
    }

}