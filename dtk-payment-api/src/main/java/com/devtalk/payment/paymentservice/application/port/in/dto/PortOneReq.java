package com.devtalk.payment.paymentservice.application.port.in.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class PortOneReq {
    @Getter
    @Builder
    public static class PortOneLinkReq{
        @JsonProperty("payment_info")
        private PaymentInfo payment_info;

        @JsonProperty("expired_at")
        private Long expired_at;
    }

    @Getter
    @Builder
    public static class PortOneStringReq{
        @JsonProperty("payment_info")
        private String paymentInfo;

        @JsonProperty("expired_at")
        private Long expiredAt;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PaymentInfo {
        @JsonProperty("title")
        private String consultationType;
        @JsonProperty("user_code")
        private String impUid;
        @JsonProperty("amount")
        private Integer amount;
        @JsonProperty("merchant_uid")
        private String merchantId;
        @JsonProperty("name")
        private String consultantName;
        @JsonProperty("currency")
        private String currency;
        @JsonProperty("buyer_name")
        private String consulterName;
        @JsonProperty("buyer_tel")
        private String consulterTel;
        @JsonProperty("buyer_email")
        private String consulterEmail;
        @JsonProperty("custom_data")
        private String customData;
        @JsonProperty("noticeUrl")
        private String webhookUrl;
        @JsonProperty("pay_methods")
        private List<PayMethod> payMethods;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PayMethod {
        @JsonProperty("pg")
        private String pg;
        @JsonProperty("pay_method")
        private String payMethod;
        @JsonProperty("label")
        private String label;
    }

}

