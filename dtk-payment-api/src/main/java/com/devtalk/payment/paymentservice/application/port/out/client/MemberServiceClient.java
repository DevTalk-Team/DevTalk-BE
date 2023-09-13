package com.devtalk.payment.paymentservice.application.port.out.client;

import com.devtalk.payment.paymentservice.application.port.out.client.dto.MemberRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.devtalk.payment.paymentservice.application.port.out.client.dto.MemberRes.*;

@FeignClient(name = "member-service")
public interface MemberServiceClient {
    @GetMapping("/member/profile/{memberId}")
    MemberRes<MemberInfoRes> getMemberInfo(@PathVariable Long memberId);
}
