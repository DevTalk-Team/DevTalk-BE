//package com.devtalk.member.memberservice.unittest;
//
//import com.devtalk.member.memberservice.member.application.SignUpService;
//import com.devtalk.member.memberservice.member.application.port.in.dto.SignUpReq;
//import com.devtalk.member.memberservice.member.application.port.out.repository.CategoryRepo;
//import com.devtalk.member.memberservice.member.application.port.out.repository.MemberCategoryRepo;
//import com.devtalk.member.memberservice.member.application.port.out.repository.MemberRepo;
//import com.devtalk.member.memberservice.member.application.validator.SignUpValidator;
//import com.devtalk.member.memberservice.member.domain.category.MemberCategory;
//import com.devtalk.member.memberservice.member.domain.member.Member;
//import com.devtalk.member.memberservice.member.domain.member.MemberType;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Spy;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class SignUpServiceUnitTest {
//    @InjectMocks
//    SignUpService signUpService;
//    @Spy
//    BCryptPasswordEncoder bCryptPasswordEncoder;
//    @Mock SignUpValidator signUpValidator;
//    @Mock MemberRepo memberRepo;
//    @Mock MemberCategoryRepo memberCategoryRepo;
//    @Mock CategoryRepo categoryRepo;
//
//    @Test
//    @DisplayName("회원가입 성공")
//    void  회원가입_성공() {
//        // given
//        List<String> category = getCategories();
//
//        SignUpReq req = getSignUpReq(category);
//
//        // when
////        signUpService.signUp(req);
//        doReturn(Member.createMember(req, bCryptPasswordEncoder)).when(memberRepo).save(any(Member.class));
//        Member member = signUpService.signUp(req);
//
//        // then
//        assertThat(member.getEmail()).isEqualTo(req.getEmail());
//        assertThat(bCryptPasswordEncoder.matches(req.getPassword(), member.getPassword())).isTrue();
//
//        // verify
//        verify(memberRepo, times(1)).save(any(Member.class));
//        verify(memberCategoryRepo, times(category.size())).save(any(MemberCategory.class));
//        verify(categoryRepo, times(category.size())).findByCategory(any(String.class));
////        verify(bCryptPasswordEncoder, times(1)).encode(any(String.class));
//    }
//
//    @Test
//    @DisplayName("회원가입 실패 - 이메일 중복")
//    void 이메일_중복() {
//        // given
//        List<String> category = getCategories();
//        SignUpReq req = getSignUpReq(category);
//        signUpService.signUp(req);
//
//        // when
//        SignUpReq req2 = getSignUpReq(category);
//        doReturn(true).when(memberRepo).existsByEmail(req2.getEmail());
//        boolean isDuplicated = signUpService.checkDuplicatedEmail(req2.getEmail());
//
//        // then
//        assertThat(isDuplicated).isTrue();
//    }
//
//    private static SignUpReq getSignUpReq(List<String> category) {
//        SignUpReq req = SignUpReq.builder()
//                .memberType(MemberType.CONSULTER)
//                .name("멘티")
//                .email("consulter@gmail.com")
//                .password("pw123456")
//                .checkPassword("pw123456")
//                .phoneNumber("010-1234-5678")
//                .category(category)
//                .build();
//        return req;
//    }
//
//    private static List<String> getCategories() {
//        List<String> category = new ArrayList<>();
//        category.add("웹");
//        category.add("DB");
//        category.add("데브옵스");
//        return category;
//    }
//}
