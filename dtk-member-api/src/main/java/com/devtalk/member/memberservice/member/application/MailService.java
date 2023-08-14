package com.devtalk.member.memberservice.member.application;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private int authCode;

    /* 인증번호 생성 */
    public void createAuthCode() {
        authCode = (int) (Math.random() * 899999) + 100000;
    }

    /* 메일 생성 */
    public MimeMessage createMessage(String email) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            mimeMessage.setFrom(new InternetAddress("devtalk@gamil.com", "Devtalk"));
            mimeMessage.setRecipients(MimeMessage.RecipientType.TO, email);
            mimeMessage.setSubject("Devtalk 회원가입 이메일 인증 번호");
            mimeMessage.setText(String.valueOf(authCode));
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return mimeMessage;
    }

    /* 메일 발송 */
    public int sendMail(String email) {
        createAuthCode();
        javaMailSender.send(createMessage(email));
        return authCode;
    }
}
