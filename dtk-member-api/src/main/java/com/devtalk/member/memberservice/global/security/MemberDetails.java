package com.devtalk.member.memberservice.global.security;

import com.devtalk.member.memberservice.member.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class MemberDetails implements UserDetails {
    // JWT 토큰을 생성할 때 필요한 정보는 MemberDetails에서 가져온다.
    private final Member member;

    @Override // 계정이 가지고 있는 권한 목록
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> {
            return "ROLE_" + member.getMemberType().toString();
        });
        return authorities;
    }

    @Override // 계정의 비밀번호
    public String getPassword() {
        return member.getPassword();
    }

    @Override // 계정의 이메일(아이디)
    public String getUsername() {
        return member.getEmail();
    }

    @Override // 계정이 만료됐는지 -> X
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override // 계정이 잠겨있는지 -> X
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override // 비밀번호가 만료됐는지 -> X
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override // 계정이 활성화돼 있는지 -> O
    public boolean isEnabled() {
        return true;
    }
}
