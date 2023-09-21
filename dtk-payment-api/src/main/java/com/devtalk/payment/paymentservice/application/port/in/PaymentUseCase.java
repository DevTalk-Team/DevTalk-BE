package com.devtalk.payment.paymentservice.application.port.in;

import com.devtalk.payment.paymentservice.adapter.in.web.dto.ConsultationInput;
import com.devtalk.payment.paymentservice.adapter.in.web.dto.PaymentInput;
import com.devtalk.payment.paymentservice.adapter.in.web.dto.PaymentInput.WebhookInput;
import com.devtalk.payment.paymentservice.application.port.in.dto.PaymentReq;
import com.devtalk.payment.paymentservice.application.port.in.dto.PaymentRes;
import com.devtalk.payment.paymentservice.domain.consultation.Consultation;
import com.devtalk.payment.paymentservice.domain.payment.Payment;
import com.siot.IamportRestClient.response.IamportResponse;

import static com.devtalk.payment.paymentservice.application.port.in.dto.PaymentReq.*;
import static com.devtalk.payment.paymentservice.application.port.in.dto.PaymentRes.*;

public interface PaymentUseCase {
    String getToken();

    String getPaymentLink(Long consultationId);

    void updatePaymentStatus(WebhookReq webhookReq);

    PaymentSearchRes searchPaymentInfo(Long consultationId);

    void createPaymentInfo(ConsultationInput consultationInput);
}
