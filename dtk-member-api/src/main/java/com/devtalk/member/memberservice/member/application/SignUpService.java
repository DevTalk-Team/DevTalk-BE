package com.devtalk.member.memberservice.member.application;

import com.devtalk.member.memberservice.member.application.port.in.SignUpUseCase;
import com.devtalk.member.memberservice.member.application.port.in.dto.SignUpReq;
import com.devtalk.member.memberservice.member.application.port.out.repository.SignUpCommandableRepo;
import com.devtalk.member.memberservice.member.application.validator.SignUpValidator;
import com.devtalk.member.memberservice.member.domain.Consultant;
import com.devtalk.member.memberservice.member.domain.Consulter;
import com.devtalk.member.memberservice.member.domain.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SignUpService implements SignUpUseCase {

    private final SignUpCommandableRepo signUpCommandableRepo;
    private final SignUpValidator signUpValidator;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void consulterSignUp(SignUpReq req) {
        signUpValidator.validate(req);
        Consulter consulter = Consulter.builder()
                .roleType(req.getRoleType())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .name(req.getName())
                .phoneNumber(req.getPhoneNumber())
                .field(req.getField().toString())
                .birthDate(req.getBirthDate())
                .build();
        signUpCommandableRepo.save(consulter);
    }

    @Override
    public void consultantSignUp(SignUpReq req) {
        signUpValidator.validate(req);
        Consultant consultant = Consultant.builder()
                .roleType(req.getRoleType())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .name(req.getName())
                .phoneNumber(req.getPhoneNumber())
                .field(req.getField().toString())
                .company(req.getCompany())
                .build();
        signUpCommandableRepo.save(consultant);
    }

    @Override
    public boolean checkDuplicatedEmail(String email) {
        return signUpCommandableRepo.existsByEmail(email);
    }

}
