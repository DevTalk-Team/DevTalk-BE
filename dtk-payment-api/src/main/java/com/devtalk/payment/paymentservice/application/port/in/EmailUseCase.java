package com.devtalk.payment.paymentservice.application.port.in;

import com.devtalk.payment.paymentservice.application.port.in.dto.EmailMessageRes;

public interface EmailUseCase {
    void send(EmailMessageRes emailMessageRes);
}
