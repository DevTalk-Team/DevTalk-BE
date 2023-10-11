package com.devtalk.board.consultationboardservice.board.application.port.out;


import com.devtalk.board.consultationboardservice.board.application.port.in.dto.MemberRes;

public interface MemberUseCase {
    MemberRes.ProfileRes findUser(String email);
}
