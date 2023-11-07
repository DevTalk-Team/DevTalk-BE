package com.devtalk.member.memberservice.member.application;

import com.devtalk.member.memberservice.global.util.RedisUtil;
import com.devtalk.member.memberservice.member.application.port.in.FindProfileUseCase;
import com.devtalk.member.memberservice.member.application.port.out.repository.MemberQueryableRepo;
import com.devtalk.member.memberservice.member.application.validator.FindProfileValidator;
import com.devtalk.member.memberservice.member.domain.member.Member;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FindProfileService implements FindProfileUseCase {

    private final MemberQueryableRepo memberQueryableRepo;
    private final FindProfileValidator validator;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtil redisUtil;
    private String tempPassword;

    @Override
    public String findEmail(String name, String phoneNumber) {
        validator.findEmailValidate(name, phoneNumber);
        return memberQueryableRepo.findEmailByNameAndPhoneNumber(name, phoneNumber);
    }

    @Override
    public void sendTempPassword(String name, String email) {
        Member member = validator.sendTempPasswordValidate(name, email);
        javaMailSender.send(createMessage(email));
        redisUtil.setDataExpire(tempPassword, email, 500000);
    }

    MimeMessage createMessage(String email) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            tempPassword = createTempPassword();

            mimeMessage.setFrom(new InternetAddress("devtalk@gamil.com", "Devtalk"));
            mimeMessage.setRecipients(MimeMessage.RecipientType.TO, email);
            mimeMessage.setSubject("Devtalk 임시 비밀번호 발급");
            mimeMessage.setText(tempPassword + "\n5분 내로 재설정해주세요.");
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return mimeMessage;
    }

     String createTempPassword() {
         char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
         'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'r',
         'w', 'x', 'y', 'z'};
         // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
         int idx = 0;
         tempPassword = "";
         for (int i = 0; i < 10; i++) {
             idx = (int) (charSet.length * Math.random());
             tempPassword += charSet[idx];
         }
         return tempPassword;
    }

    @Transactional
    @Override
    public void changePassword(String password, String newPassword) {
        Member member = validator.changePasswordValidate(password, newPassword);
        member.updatePassword(passwordEncoder.encode(newPassword));
    }
}
