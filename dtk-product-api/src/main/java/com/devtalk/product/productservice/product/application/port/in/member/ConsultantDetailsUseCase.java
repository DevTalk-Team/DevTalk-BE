package com.devtalk.product.productservice.product.application.port.in.member;

import com.devtalk.product.productservice.product.application.port.in.dto.ConsultantPrivacyReq;
public interface ConsultantDetailsUseCase {
    public void privacyUpdate(ConsultantPrivacyReq consultantPrivacyReq);
}
