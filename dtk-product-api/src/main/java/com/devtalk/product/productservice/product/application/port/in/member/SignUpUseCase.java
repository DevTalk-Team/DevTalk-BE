package com.devtalk.product.productservice.product.application.port.in.member;

import com.devtalk.product.productservice.product.application.port.in.dto.MemberReq;

public interface SignUpUseCase {
    public void signUpMember(MemberReq memberReq);
}
