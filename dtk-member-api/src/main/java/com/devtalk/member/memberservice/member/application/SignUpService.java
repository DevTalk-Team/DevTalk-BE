package com.devtalk.member.memberservice.member.application;

import com.devtalk.member.memberservice.member.adapter.out.persistence.CounseleeJpaEntity;
import com.devtalk.member.memberservice.member.application.port.in.SignUpUseCase;
import com.devtalk.member.memberservice.member.application.port.in.dto.CounseleeSignUpReq;
import com.devtalk.member.memberservice.member.application.port.out.producer.SaveMemberProducerPort;
import com.devtalk.member.memberservice.member.application.port.out.repository.CounseleeCommandableRepo;
import com.devtalk.member.memberservice.member.domain.Counselee;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SignUpService implements SignUpUseCase {

    private final CounseleeCommandableRepo counseleeCommandableRepo; // 아웃고잉 포트 인터페이스: 새로운 멤버 저장

    @Override
    public void signUp(CounseleeSignUpReq req) {

        Counselee domain = new Counselee(req.getEmail(), req.getPassword(), req.getPhoneNumber(), req.getBirthDate());

        counseleeCommandableRepo.save(domain);
    }
}
