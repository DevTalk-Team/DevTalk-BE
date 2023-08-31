package com.devtalk.payment.paymentservice.application.port.in;

import com.devtalk.payment.paymentservice.application.port.in.dto.EmailMessageReq;
import com.devtalk.payment.paymentservice.domain.consultation.Consultation;

public interface EmailUseCase {
    void send(EmailMessageReq emailMessageReq);

    String getEmailHtmlPaidInfo(Consultation consultation);

    void sendPaymentSuccessEmail(Consultation consultation);
}
