package com.devtalk.product.productservice.product.application.port.in.member;

import com.devtalk.product.productservice.product.application.port.in.dto.ConsultantReq;
import com.devtalk.product.productservice.product.application.port.in.dto.ConsulterReq;
import com.devtalk.product.productservice.product.application.port.in.dto.MemberReq;
import com.devtalk.product.productservice.product.domain.member.Consultant;

public interface SignUpUseCase {
    public void signUpMember(MemberReq memberReq);
    public void signupConsultant(ConsultantReq consultantReq);
    public void signupConsulter(ConsulterReq consulterReq);


    }
