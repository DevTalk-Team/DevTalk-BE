package com.devtalk.product.productservice.product.application.service.member;

import com.devtalk.product.productservice.product.application.port.in.dto.MemberReq;
import com.devtalk.product.productservice.product.application.port.in.member.SignUpUseCase;
import com.devtalk.product.productservice.product.application.port.out.repository.MemberRepo;
import com.devtalk.product.productservice.product.domain.member.Consultant;
import com.devtalk.product.productservice.product.domain.member.Consulter;
import com.devtalk.product.productservice.product.domain.member.MemberType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SignUpService implements SignUpUseCase {
    private final MemberRepo memberRepo;

    @Transactional
    public void signUpMember(MemberReq memberReq){
        if (memberReq.getMemberType() == MemberType.CONSULTER){
            Consulter consulter = Consulter.builder()
                    .id(memberReq.getId())
                    .phoneNumber(memberReq.getPhoneNumber())
                    .memberType(memberReq.getMemberType())
                    .name(memberReq.getName())
                    .build();
            memberRepo.save(consulter);
        }
        if (memberReq.getMemberType() == MemberType.CONSULTANT){
            Consultant consultant = Consultant.builder()
                    .id(memberReq.getId())
                    .phoneNumber(memberReq.getPhoneNumber())
                    .memberType(memberReq.getMemberType())
                    .name(memberReq.getName())
                    .build();
            memberRepo.save(consultant);
        }
    }

}
