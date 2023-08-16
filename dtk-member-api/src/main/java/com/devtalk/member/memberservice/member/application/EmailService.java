package com.devtalk.member.memberservice.member.application;

import com.devtalk.member.memberservice.global.error.ErrorCode;
import com.devtalk.member.memberservice.global.error.exception.AuthCodeMismatchingException;
import com.devtalk.member.memberservice.global.util.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;
    private int authCode;

    /* 인증번호 생성 */
    public int createAuthCode() {
        return (int) (Math.random() * 888888) + 111111;
    }

    /* 메일 생성 */
    public MimeMessage createMessage(String email) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            int authCode = createAuthCode();

            mimeMessage.setFrom(new InternetAddress("devtalk@gamil.com", "Devtalk"));
            mimeMessage.setRecipients(MimeMessage.RecipientType.TO, email);
            mimeMessage.setSubject("Devtalk 회원가입 이메일 인증 번호");
            mimeMessage.setText(String.valueOf(authCode) + "\n인증 번호는 3분동안 유효합니다.");

            redisUtil.setDataExpire(email, String.valueOf(authCode), 60 * 3L);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return mimeMessage;
    }

    /* 메일 발송 */
    public void sendMail(String email) {
        // 기존 인증 키가 있으면 삭제
        if (redisUtil.existData(email)) {
            redisUtil.deleteData(email);
        }
        javaMailSender.send(createMessage(email));
    }

    /* 인증코드 일치 확인 */
    public void verifyAuthCode(String email, String authCode) {
        if (isVerify(email, authCode)) {
            throw new AuthCodeMismatchingException(ErrorCode.AUTH_CODE_MISMATCHING);
        }
        redisUtil.deleteData(email);
    }

    public boolean isVerify(String email, String authCode) {
        return !(redisUtil.getData(email).equals(authCode));
    }
}
