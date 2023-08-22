package com.devtalk.member.memberservice.member.application.port.in.dto;

import com.devtalk.member.memberservice.global.jwt.AuthDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenDto {
    String accessToken;
    String refreshToken;

    public static TokenDto of(String accessToken, String refreshToken) {
        return new TokenDto(accessToken, refreshToken);
    }

}
