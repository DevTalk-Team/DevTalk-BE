package com.devtalk.payment.paymentservice.application.port.out.client;

import com.devtalk.payment.paymentservice.application.port.out.client.dto.MemberRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "member-service")
public interface MemberServiceClient {

    @GetMapping("/member/profile/email/{email}")
    MemberRes.ProfileRes getMemberByEmail(@PathVariable String email);

    @GetMapping("/member/profile/{userId}")
    MemberRes getUserByUserId(@PathVariable Long userId);
}
