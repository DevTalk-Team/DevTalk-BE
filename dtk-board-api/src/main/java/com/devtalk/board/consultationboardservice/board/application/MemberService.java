package com.devtalk.board.consultationboardservice.board.application;

import com.devtalk.board.consultationboardservice.board.application.port.in.dto.MemberRes;
import com.devtalk.board.consultationboardservice.board.application.port.out.MemberUseCase;
import com.devtalk.board.consultationboardservice.board.application.port.out.client.MemberServiceClient;
import com.devtalk.board.consultationboardservice.global.error.ErrorCode;
import com.devtalk.board.consultationboardservice.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.devtalk.board.consultationboardservice.board.application.port.in.dto.MemberRes.*;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberUseCase {
    private final MemberServiceClient memberServiceClient;

    public MemberRes.ProfileRes findUser(String email) {
        return Optional.ofNullable(memberServiceClient.getMemberByEmail(email))
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));
    }

    public Long findUserId(String email) {
        MemberRes.ProfileRes memberInfo = findUser(email);
        return memberInfo.getId();
    }
}
