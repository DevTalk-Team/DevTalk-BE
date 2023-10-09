package com.devtalk.payment.paymentservice.application.port.out;

import com.devtalk.payment.paymentservice.application.port.out.client.dto.MemberRes;

public interface MemberUseCase {
    MemberRes.ProfileRes findUser(String email);

    Long findUserId(String email);
}
