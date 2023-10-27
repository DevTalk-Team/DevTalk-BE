package com.devtalk.consultation.consultationservice.consultation.application.port.out.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "payment-service") // Discovery Server에 등록된 서비스 이름
public interface PaymentServiceClient {

    @DeleteMapping("{consultationId}/cancel")
    void refund(@PathVariable Long consultationId);
}
