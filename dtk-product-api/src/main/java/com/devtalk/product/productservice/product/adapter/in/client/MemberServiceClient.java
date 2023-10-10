package com.devtalk.product.productservice.product.adapter.in.client;

import com.devtalk.product.productservice.product.application.port.in.dto.MemberReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "member-service")
public interface MemberServiceClient {
    @GetMapping("/member/profile/consultant/{consultant}")
    MemberReq.ConsultantReq getConsultantInfo(@PathVariable Long consultant);
    @GetMapping("/member/profile/email/{email}")
    MemberReq.ProfileReq getMemberByEmail(@PathVariable String email);
}
