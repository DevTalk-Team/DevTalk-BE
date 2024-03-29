package com.devtalk.auth.authservice.jwt;

import com.devtalk.auth.authservice.member.MemberRes;
import com.devtalk.auth.authservice.member.MemberServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
    private final MemberServiceClient memberServiceClient;

    @Override
    public MemberDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("[loadUserByUsername] 수행. email : {}", email);
        MemberRes member = memberServiceClient.getMemberByEmail(email);
        return new MemberDetails(member);
    }
}
