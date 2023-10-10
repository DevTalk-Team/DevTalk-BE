package com.devtalk.payment.paymentservice.application.port.in.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

public class PortOneRes {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PortOneLinkRes {
        private String shortenedUrl;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PortOneTokenRes{
        private Integer code;
        private String message;
        private Response response;

        @Getter
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Response{
            @JsonProperty("access_token")
            private String accessToken;
            private Integer now;
            @JsonProperty("expired_at")
            private Integer expiredAt;
        }
    }

    @Getter
    public static class PortOneRefundRes{
        private Integer code;
        private String message;
    }
}
