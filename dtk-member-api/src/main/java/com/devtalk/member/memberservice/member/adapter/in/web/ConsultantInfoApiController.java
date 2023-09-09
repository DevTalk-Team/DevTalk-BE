package com.devtalk.member.memberservice.member.adapter.in.web;

import com.devtalk.member.memberservice.global.jwt.JwtTokenProvider;
import com.devtalk.member.memberservice.global.success.SuccessResponse;
import com.devtalk.member.memberservice.global.success.SuccessResponseNoResult;
import com.devtalk.member.memberservice.member.adapter.in.web.dto.ConsultantInput;
import com.devtalk.member.memberservice.member.adapter.in.web.dto.ConsultationCategoryInput;
import com.devtalk.member.memberservice.member.application.port.in.ConsultantInfoUseCase;
import com.devtalk.member.memberservice.member.application.port.in.dto.ConsultantRegionDto;
import com.devtalk.member.memberservice.member.application.port.in.dto.ConsultantRes;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.devtalk.member.memberservice.global.success.SuccessCode.*;

@RestController
@RequestMapping("/member/consultant")
@RequiredArgsConstructor
public class ConsultantInfoApiController {
    private final ConsultantInfoUseCase consultantInfoUseCase;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/info")
    public ResponseEntity<?> getInfo(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        ConsultantRes.InfoRes infoRes = consultantInfoUseCase.getInfo(token);
        return SuccessResponse.toResponseEntity(CONSULTANT_INFO_SUCCESS, infoRes);
    }

    @PutMapping("/info")
    public ResponseEntity<?> updateInfo(@RequestBody ConsultantInput.InfoInput input, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        ConsultantRes.InfoRes infoRes = consultantInfoUseCase.updateInfo(token, input);
        return SuccessResponse.toResponseEntity(CONSULTANT_INFO_UPDATE_SUCCESS, infoRes);
    }

//    @GetMapping("/info/type") // 상담 가능 유형 (예: 커리어 상담, 멘토섭외 ...)
//    public ResponseEntity<?> getType(HttpServletRequest request) {
//        String token = jwtTokenProvider.resolveToken(request);
//        return SuccessResponse.toResponseEntity();
//    }

//    @PutMapping("/info/type")
//    public SuccessResponse<ConsultantTypeRes> updateType(@RequestBody ConsultantTypeInput input, HttpServletRequest request) {
//        String token = jwtTokenProvider.resolveToken(request);
//        ConsultantTypeRes res = consultantInfoUseCase.updateType(token, );
//        return new SuccessResponse<>(CONSULTANT_INFO_TYPE_UPDATE_SUCCESS, res);
//    }

    @GetMapping("/info/category") // 상담 가능 카테고리 (예: 웹, DB ...)
    public ResponseEntity<?> getCategory(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        List<String> res = consultantInfoUseCase.getCategory(token);
        return SuccessResponse.toResponseEntity(CONSULTANT_INFO_CATEGORY_SUCCESS, res);
    }

    @PostMapping("/info/category")
    public ResponseEntity<?> updateCategory(@RequestBody ConsultationCategoryInput input, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        consultantInfoUseCase.updateCategory(token, input); // TODO input 검증
        return SuccessResponseNoResult.toResponseEntity(CONSULTANT_INFO_CATEGORY_UPDATE_SUCCESS);
    }

    @GetMapping("/info/region") // 상담 가능 지역
    public SuccessResponse<ConsultantRegionDto> getRegion() {
        return new SuccessResponse<>();
    }
}
