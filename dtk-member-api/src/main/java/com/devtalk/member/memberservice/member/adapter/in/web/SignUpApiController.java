package com.devtalk.member.memberservice.member.adapter.in.web;

import com.devtalk.member.memberservice.member.adapter.in.web.dto.CounseleeSignUpInput;
import com.devtalk.member.memberservice.member.adapter.in.web.dto.CounseleeSignUpOutput;
import com.devtalk.member.memberservice.member.application.port.in.SignUpUseCase;
import com.devtalk.member.memberservice.member.application.port.in.dto.CounseleeSignUpReq;
import com.devtalk.member.memberservice.member.domain.Counselee;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * http request를 Java 객체로 매핑
 * 권한 검사
 * 입력 유효성 검증
 * 입력 값을 Use Case용 입력모델로 매핑
 * 유스케이스 호출
 * 유스케이스 결과를 http로 매핑
 * http response 반환
 */

@RestController
@RequiredArgsConstructor
public class SignUpApiController {

    private final SignUpUseCase signUpUseCase;

    /* 멘티 회원가입 */
    @PostMapping("/counselee")
    public ResponseEntity<CounseleeSignUpOutput> counseleeSignUp(@Valid @RequestBody CounseleeSignUpInput input, BindingResult bindingResult) {
        //command(req) 생성
        CounseleeSignUpReq req = new CounseleeSignUpReq(input.getEmail(), input.getPassword(), input.getPhoneNumber(), input.getBirthDate());

        //usecase 함수 호출
        signUpUseCase.signUp(req);

        CounseleeSignUpOutput output = new CounseleeSignUpOutput("0111", "멘티 회원가입 성공");

        return ResponseEntity.ok().body(output);
    }


}
