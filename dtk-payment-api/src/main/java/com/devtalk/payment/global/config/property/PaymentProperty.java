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
    private String impKey;
    private String impSecret;
    private String impUid;
    private String webhookUrl;

    public PaymentProperty(
            @Value("${portone.imp_key}") String impKey,
            @Value("${portone.imp_secret}") String impSecret,
            @Value("${portone.imp_uid}") String impUid,
            @Value("${portone.webhook_url}") String webhookUrl) {
        this.impKey = impKey;
        this.impSecret = impSecret;
        this.impUid = impUid;
        this.webhookUrl = webhookUrl;
    }

}