package com.devtalk.payment.paymentservice.application;

import com.devtalk.payment.paymentservice.application.port.in.EmailUseCase;
import com.devtalk.payment.paymentservice.application.port.in.dto.EmailMessageReq;
import com.devtalk.payment.paymentservice.domain.consultation.Consultation;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@Slf4j
@Component
@RequiredArgsConstructor
class EmailService implements EmailUseCase {
    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;

    @Override
    public String getEmailHtmlPaidInfo(Consultation consultation) {
        Context context = new Context();
        context.setVariable("consulter", consultation.getConsulterName());
        context.setVariable("consultant", consultation.getConsultantName());
        context.setVariable("message", "결제에 성공했습니다.");

        return templateEngine.process("mail/payment-email", context);
    }

    @Override
    public void sendPaymentSuccessEmail(Consultation consultation) {
        String content = getEmailHtmlPaidInfo(consultation);

        EmailMessageReq emailMessageReq = EmailMessageReq.builder()
                .to(consultation.getConsulterEmail())
                .subject("데브톡 - 결제 내역")
                .message(content)
                .build();

        send(emailMessageReq);
    }

    @Override
    public void send(EmailMessageReq emailMessageReq) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailMessageReq.getTo());
            mimeMessageHelper.setSubject(emailMessageReq.getSubject());
            mimeMessageHelper.setText(emailMessageReq.getMessage(), true); // 두 번째 파라미터는 html인지 여부
            javaMailSender.send(mimeMessage);
            log.info("sent email {}", emailMessageReq.getMessage());
        } catch (MessagingException e) {
            log.error("failed to send email", e);
        }
    }
}
