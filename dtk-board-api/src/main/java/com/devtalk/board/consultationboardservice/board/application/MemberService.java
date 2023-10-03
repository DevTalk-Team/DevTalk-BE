package com.devtalk.board.consultationboardservice.board.application;

import com.devtalk.board.consultationboardservice.board.application.port.in.dto.MemberRes;
import com.devtalk.board.consultationboardservice.board.application.port.out.client.MemberServiceClient;
import com.devtalk.board.consultationboardservice.global.error.ErrorCode;
import com.devtalk.board.consultationboardservice.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.devtalk.board.consultationboardservice.board.application.port.in.dto.MemberRes.*;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberServiceClient memberServiceClient;

    public MemberInfoRes findUser(Long userId) {
        MemberRes memberRes = Optional.ofNullable(memberServiceClient.findUser(userId))
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));
        return memberRes.getResult();
    }
}
