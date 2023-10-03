package com.devtalk.auth.authservice.member;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("member-service")
public interface MemberServiceClient {
    @GetMapping("/member/profile/email/{email}")
    MemberRes getMemberByEmail(@PathVariable String email);
}
