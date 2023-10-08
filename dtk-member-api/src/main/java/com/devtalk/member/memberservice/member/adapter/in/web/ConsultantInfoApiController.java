package com.devtalk.member.memberservice.member.adapter.in.web;

import com.devtalk.member.memberservice.global.jwt.JwtTokenProvider;
import com.devtalk.member.memberservice.global.jwt.MemberDetails;
import com.devtalk.member.memberservice.global.success.SuccessResponse;
import com.devtalk.member.memberservice.global.success.SuccessResponseNoResult;
import com.devtalk.member.memberservice.member.adapter.in.web.dto.ConsultantInput;
import com.devtalk.member.memberservice.member.adapter.out.producer.KafkaProducer;
import com.devtalk.member.memberservice.member.application.port.in.ConsultantInfoUseCase;
import com.devtalk.member.memberservice.member.application.port.out.dto.ConsultantRes;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.devtalk.member.memberservice.global.success.SuccessCode.*;

@Slf4j
@RestController
@RequestMapping("/member/consultant")
@RequiredArgsConstructor
public class ConsultantInfoApiController {
    private final ConsultantInfoUseCase consultantInfoUseCase;
    private final JwtTokenProvider jwtTokenProvider;
    private final KafkaProducer kafkaProducer;
    // TODO @AuthenticationPrincipal MemberDetails memberDetails 리팩토링
    @GetMapping("/info")
    public ResponseEntity<?> getInfo(@AuthenticationPrincipal MemberDetails memberDetails) {
        ConsultantRes.InfoRes res = consultantInfoUseCase.getInfo(memberDetails.getUsername());
        return SuccessResponse.toResponseEntity(CONSULTANT_INFO_SUCCESS, res);
    }

    @PutMapping("/info")
    public ResponseEntity<?> updateInfo(@RequestBody ConsultantInput.InfoInput input,
                                        @AuthenticationPrincipal MemberDetails memberDetails) {
        ConsultantRes.InfoRes res = consultantInfoUseCase.updateInfo(memberDetails.getUsername(), input);
        kafkaProducer.sendConsultantInfo(res.toEntity());
        return SuccessResponse.toResponseEntity(CONSULTANT_INFO_UPDATE_SUCCESS, res);
    }

    @GetMapping("/info/type") // 상담 가능 유형 (예: 커리어 상담, 멘토섭외 ...)
    public ResponseEntity<?> getType(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        List<String> res = consultantInfoUseCase.getType(token);
        return SuccessResponse.toResponseEntity(CONSULTANT_INFO_TYPE_SUCCESS, res);
    }

    @PutMapping("/info/type")
    public ResponseEntity<?> updateType(@RequestBody ConsultantInput.ListInput input, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        consultantInfoUseCase.updateType(token, input);
        return SuccessResponseNoResult.toResponseEntity(CONSULTANT_INFO_TYPE_UPDATE_SUCCESS);
    }

    @GetMapping("/info/category") // 상담 가능 카테고리 (예: 웹, DB ...)
    public ResponseEntity<?> getCategory(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        List<String> res = consultantInfoUseCase.getCategory(token);
        return SuccessResponse.toResponseEntity(CONSULTANT_INFO_CATEGORY_SUCCESS, res);
    }

    @PutMapping("/info/category")
    public ResponseEntity<?> updateCategory(@RequestBody ConsultantInput.ListInput input, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        consultantInfoUseCase.updateCategory(token, input); // TODO input 검증
        return SuccessResponseNoResult.toResponseEntity(CONSULTANT_INFO_CATEGORY_UPDATE_SUCCESS);
    }

    @GetMapping("/info/region") // 상담 가능 지역
    public ResponseEntity<?> getRegion(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        List<String> res = consultantInfoUseCase.getRegion(token);
        return SuccessResponse.toResponseEntity(CONSULTANT_INFO_REGION_SUCCESS, res);
    }

    @PutMapping("/info/region")
    public ResponseEntity<?> updateRegion(@RequestBody ConsultantInput.ListInput input, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        consultantInfoUseCase.updateRegion(token, input); // TODO input 검증
        return SuccessResponseNoResult.toResponseEntity(CONSULTANT_INFO_REGION_UPDATE_SUCCESS);
    }
}
