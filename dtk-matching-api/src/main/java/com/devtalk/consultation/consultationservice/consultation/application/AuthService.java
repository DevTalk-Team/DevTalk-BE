package com.devtalk.consultation.consultationservice.consultation.application;

import com.devtalk.consultation.consultationservice.consultation.application.port.in.AuthUseCase;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.client.MemberServiceClient;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.client.dto.MemberReq;
import com.devtalk.consultation.consultationservice.global.error.ErrorCode;
import com.devtalk.consultation.consultationservice.global.error.execption.NotFoundException;
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
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_MEMBER));
    }

    public Long auth(String userEmail) {
        MemberReq.ProfileReq memberInfo = findUser(userEmail);
        return memberInfo.getId();
    }
}


