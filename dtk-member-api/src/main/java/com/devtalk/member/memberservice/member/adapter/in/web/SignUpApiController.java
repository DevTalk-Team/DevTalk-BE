package com.devtalk.member.memberservice.member.adapter.in.web;

import com.devtalk.member.memberservice.member.adapter.in.web.dto.CounseleeSignUpInput;
import com.devtalk.member.memberservice.member.adapter.in.web.dto.CounseleeSignUpOutput;
import com.devtalk.member.memberservice.member.application.port.in.SignUpUseCase;
import com.devtalk.member.memberservice.member.application.port.in.dto.CounseleeSignUpReq;
import com.devtalk.member.memberservice.member.domain.Counselee;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CounseleeSignUpOutput> counseleeSignUp(@RequestBody CounseleeSignUpInput dto) {
        //command(req) 생성
        CounseleeSignUpReq req = new CounseleeSignUpReq(dto.getEmail(), dto.getPassword(), dto.getPhoneNumber(), dto.getBirthDate());

        //usecase 함수 호출
        signUpUseCase.signUp(req);

        return ResponseEntity.ok().build();
    }



}
