package com.devtalk.member.memberservice.member.application;

import com.devtalk.member.memberservice.global.util.RedisUtil;
import com.devtalk.member.memberservice.member.application.port.in.FindProfileUseCase;
import com.devtalk.member.memberservice.member.application.port.out.repository.MemberRepo;
import com.devtalk.member.memberservice.member.application.validator.FindProfileValidator;
import com.devtalk.member.memberservice.member.domain.member.Member;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FindProfileService implements FindProfileUseCase {

    private final MemberRepo memberRepo;
    private final FindProfileValidator validator;
    private final JavaMailSender javaMailSender;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private String tempPassword; // 암호화까지 완료한 상태

    @Override
    public String findEmail(String name, String phoneNumber) {
        validator.findEmailValidate(name, phoneNumber);
        log.info("이메일 찾기 {}", memberRepo.findEmailByNameAndPhoneNumber(name, phoneNumber));
        return memberRepo.findEmailByNameAndPhoneNumber(name, phoneNumber);
    }

    @Override
    public void sendTempPassword(String name, String email) {
        Member member = validator.sendTempPasswordValidate(name, email);
        javaMailSender.send(createMessage(email));
        member.updatePassword(tempPassword);
    }

    MimeMessage createMessage(String email) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            tempPassword = createTempPassword();

            mimeMessage.setFrom(new InternetAddress("devtalk@gamil.com", "Devtalk"));
            mimeMessage.setRecipients(MimeMessage.RecipientType.TO, email);
            mimeMessage.setSubject("Devtalk 임시 비밀번호 발급");
            mimeMessage.setText(tempPassword + "\n임시 비밀번호로 로그인 후, 새로운 비밀번호를 설정해주세요.");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return mimeMessage;
    }

     String createTempPassword() {
         char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
         // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
         int idx = 0;
         tempPassword = "";
         for (int i = 0; i < 10; i++) {
             idx = (int) (charSet.length * Math.random());
             tempPassword += charSet[idx];
         }
         tempPassword = bCryptPasswordEncoder.encode(tempPassword);
         return tempPassword;
    }

    @Override
    public void changePassword(String password, String newPassword) {
        // TODO password가 임시 password가 맞는지 확인 후
        validator.changePasswordValidate(password);
        // newPassword로 변경
//        member.updatePassword(newPassword)
    }
}
