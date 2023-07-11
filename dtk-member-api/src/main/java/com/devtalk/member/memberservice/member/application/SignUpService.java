package com.devtalk.member.memberservice.member.application;

import com.devtalk.member.memberservice.member.application.port.in.SignUpUseCase;
import com.devtalk.member.memberservice.member.application.port.in.dto.CounseleeSignUpReq;
import com.devtalk.member.memberservice.member.application.port.out.producer.SaveMemberProducerPort;
import com.devtalk.member.memberservice.member.domain.Counselee;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SignUpService implements SignUpUseCase {

//    private final SaveMemberProducerPort saveMemberProducerPort; // 아웃고잉 포트 인터페이스: 새로운 멤버 저장

    @Override
    public void signUp(CounseleeSignUpReq req) {

        Counselee counselee = new Counselee(req.getEmail(), req.getPassword(), req.getPhoneNumber(), req.getBirthDate());




    }
}
