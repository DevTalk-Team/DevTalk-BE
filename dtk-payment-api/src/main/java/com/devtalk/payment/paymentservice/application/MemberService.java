package com.devtalk.payment.paymentservice.application;

import com.devtalk.payment.global.code.ErrorCode;
import com.devtalk.payment.global.error.exception.NotFoundException;
import com.devtalk.payment.paymentservice.application.port.out.MemberUseCase;
import com.devtalk.payment.paymentservice.application.port.out.client.MemberServiceClient;
import com.devtalk.payment.paymentservice.application.port.out.client.dto.MemberRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberUseCase {
    private final MemberServiceClient memberServiceClient;

    public MemberRes.ProfileRes findUser(String email) {
        return Optional.ofNullable(memberServiceClient.getMemberByEmail(email))
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_MEMBER));
    }

    public Long findUserId(String email) {
        MemberRes.ProfileRes memberInfo = findUser(email);
        return memberInfo.getId();
    }
}
