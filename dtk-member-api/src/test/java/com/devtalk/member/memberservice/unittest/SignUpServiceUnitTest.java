package com.devtalk.member.memberservice.unittest;

import com.devtalk.member.memberservice.member.adapter.out.persistence.MemberJpaEntity;
import com.devtalk.member.memberservice.member.adapter.out.persistence.SignUpCommandRepo;
import com.devtalk.member.memberservice.member.application.SignUpService;
import com.devtalk.member.memberservice.member.application.port.in.dto.SignUpReq;
import com.devtalk.member.memberservice.member.application.validator.SignUpValidator;
import com.devtalk.member.memberservice.member.domain.RoleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SignUpServiceUnitTest {

    @InjectMocks
    SignUpService signUpService;

    @Mock
    SignUpValidator signUpValidator;
    @Mock
    SignUpCommandRepo signUpCommandRepo;
    @Spy
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("멘티 회원가입 성공")
    void 멘티_회원가입_성공() {
        // given
        List<String> field = new ArrayList<>();
        field.add("jpa");
        field.add("spring");

        SignUpReq req = SignUpReq.builder()
                .roleType(RoleType.CONSULTER)
                .email("test@gmail.com")
                .password("qwer12!@")
                .name("멘티")
                .phoneNumber("010-1234-5678")
                .birthDate(LocalDate.parse("2000-07-11", DateTimeFormatter.ISO_DATE))
                .field(field)
                .build();

        // when
        signUpService.consulterSignUp(req);

        // then
    }
}
