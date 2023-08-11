package com.devtalk.payment.global.config;

import com.siot.IamportRestClient.IamportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IamportConfig {

    String apiKey = "7728611378883883";
    String secretKey = "Aa8S38F7UhKtohATUSV9ILy7BExATIjJg9naXorRMox6AqcGl7ycVkbCAC6WXMoxX0oymhfPhjuaAB6m";

    @Bean
    public IamportClient iamportClient() {
        return new IamportClient(apiKey, secretKey);
    }
}
