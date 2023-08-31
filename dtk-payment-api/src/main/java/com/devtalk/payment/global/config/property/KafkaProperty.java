package com.devtalk.payment.global.config.property;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class KafkaProperty {
    private final String bootstrapServer;

    public KafkaProperty(@Value("${kafka.bootstrap-server}") String bootstrapServer) {
        this.bootstrapServer = bootstrapServer;
    }
}
