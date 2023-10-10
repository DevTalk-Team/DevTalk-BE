package com.devtalk.product.productservice.product.application.service.member;


import com.devtalk.product.productservice.global.error.ErrorCode;
import com.devtalk.product.productservice.global.error.exception.NotFoundException;

import com.devtalk.product.productservice.product.adapter.in.client.MemberServiceClient;
import com.devtalk.product.productservice.product.application.port.in.dto.MemberReq;
import com.devtalk.product.productservice.product.application.port.in.product.AuthUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {
    private final MemberServiceClient memberServiceClient;

    public MemberReq.ProfileReq findUser(String userEmail){
        return Optional.ofNullable(memberServiceClient.getMemberByEmail(userEmail))
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));
    }

    public Long auth(String userEmail) {
        MemberReq.ProfileReq memberInfo = findUser(userEmail);
        return memberInfo.getId();
    }
}

