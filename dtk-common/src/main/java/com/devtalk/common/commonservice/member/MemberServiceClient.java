package com.devtalk.common.commonservice.member;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("member-service")
public interface MemberServiceClient {
    @GetMapping("/members/profile/email/{email}")
    MemberRes getMemberByEmail(@PathVariable String email);
}
