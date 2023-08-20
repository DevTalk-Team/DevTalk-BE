package com.devtalk.payment.paymentservice.application;

import com.devtalk.payment.global.code.ErrorCode;
import com.devtalk.payment.global.error.exception.NotFoundException;
import com.devtalk.payment.paymentservice.application.port.in.ConsultationUseCase;
import com.devtalk.payment.paymentservice.application.port.out.repository.ConsultationRepo;
import com.devtalk.payment.paymentservice.domain.consultation.Consultation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@Slf4j
@RequiredArgsConstructor
public
class ConsultationService implements ConsultationUseCase {
    private final ConsultationRepo consultationRepo;
    private final TemplateEngine templateEngine;

    @Override
    public Consultation searchConsultationInfo(Long consultationId) {
        return consultationRepo.findById(consultationId)
                .orElseThrow(()-> new NotFoundException(ErrorCode.NOT_FOUND_CONSULTATION));
    }

    @Override
    public String getEmailHtmlConsultationPaidInfo(Consultation consultation) {
        Context context = new Context();
        context.setVariable("counselee", consultation.getConsulter());
        context.setVariable("counselor", consultation.getConsultant());
        context.setVariable("message", "결제에 성공했습니다.");

        return templateEngine.process("mail/payment-email", context);
    }

}
