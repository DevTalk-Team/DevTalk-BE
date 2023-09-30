package com.devtalk.board.consultationboardservice.board.application.port.out.client;

import com.devtalk.board.consultationboardservice.board.application.port.in.dto.MemberRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "member-service")
public interface MemberServiceClient {
    @GetMapping("/member/profile/{memberId}")
    MemberRes findMember(@PathVariable Long memberId);
}
