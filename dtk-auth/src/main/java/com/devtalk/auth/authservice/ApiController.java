package com.devtalk.auth.authservice;

import com.devtalk.auth.authservice.member.MemberRes;
import com.devtalk.auth.authservice.member.MemberServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApiController {
    private final MemberServiceClient memberServiceClient;

    @GetMapping("/common/{email}")
    public void getMember(@PathVariable String email) {
        MemberRes res = memberServiceClient.getMemberByEmail(email);
        System.out.println("res = " + res.getEmail());
    }
}
