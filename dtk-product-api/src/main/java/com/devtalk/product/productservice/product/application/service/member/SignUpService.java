package com.devtalk.product.productservice.product.application.service.member;

import com.devtalk.product.productservice.product.application.port.in.dto.ConsultantReq;
import com.devtalk.product.productservice.product.application.port.in.dto.ConsulterReq;
import com.devtalk.product.productservice.product.application.port.in.dto.MemberReq;
import com.devtalk.product.productservice.product.application.port.in.member.SignUpUseCase;
import com.devtalk.product.productservice.product.application.port.out.repository.ConsultantRepository;
import com.devtalk.product.productservice.product.application.port.out.repository.ConsulterRepository;
import com.devtalk.product.productservice.product.application.port.out.repository.MemberRepo;
import com.devtalk.product.productservice.product.domain.member.Consultant;
import com.devtalk.product.productservice.product.domain.member.Consulter;
import com.devtalk.product.productservice.product.domain.member.MemberType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignUpService implements SignUpUseCase {
    private final ConsultantRepository consultantRepo;
    private final ConsulterRepository consulterRepo;

    @Override
    public void signUpMember(MemberReq memberReq) {

    }

    @Transactional
    public void signupConsultant(ConsultantReq consultantReq) {
        Consultant consultant = Consultant.builder()
                .phoneNumber(consultantReq.getPhoneNumber())
                .memberType(consultantReq.getMemberType())
                .name(consultantReq.getName())
                .nf2f_Price(consultantReq.getNf2f_Price())
                .f2f_Price(consultantReq.getF2f_Price())
                .region(consultantReq.getRegion())
                .build();
        consultantRepo.save(consultant);
    }

    @Transactional
    public void signupConsulter(ConsulterReq consulterReq) {
        Consulter consulter = Consulter.builder()
                .phoneNumber(consulterReq.getPhoneNumber())
                .memberType(consulterReq.getMemberType())
                .name(consulterReq.getName())
                .build();
        consulterRepo.save(consulter);
    }
}

