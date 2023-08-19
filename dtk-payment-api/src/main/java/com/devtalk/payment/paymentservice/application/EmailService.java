package com.devtalk.payment.paymentservice.application;

import com.devtalk.payment.paymentservice.application.port.in.EmailUseCase;
import com.devtalk.payment.paymentservice.application.port.in.dto.EmailMessageRes;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Component
@RequiredArgsConstructor
class EmailService implements EmailUseCase {

    private final JavaMailSender javaMailSender;

    @Override
    public void send(EmailMessageRes emailMessageRes) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailMessageRes.getTo());
            mimeMessageHelper.setSubject(emailMessageRes.getSubject());
            mimeMessageHelper.setText(emailMessageRes.getMessage(), true); // 두 번째 파라미터는 html인지 여부
            javaMailSender.send(mimeMessage);
            log.info("sent email {}", emailMessageRes.getMessage());
        } catch (MessagingException e) {
            log.error("failed to send email", e);
        }
    }
}
