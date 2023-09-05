package com.devtalk.member.memberservice.member.adapter.in.web;

import com.devtalk.member.memberservice.global.jwt.JwtTokenProvider;
import com.devtalk.member.memberservice.global.success.SuccessResponse;
import com.devtalk.member.memberservice.member.application.port.in.dto.ConsultantTypeRes;
import com.devtalk.member.memberservice.member.application.port.in.ConsultantInfoUseCase;
import com.devtalk.member.memberservice.member.application.port.in.dto.ConsultantCategoryDto;
import com.devtalk.member.memberservice.member.application.port.in.dto.ConsultantInfoRes;
import com.devtalk.member.memberservice.member.application.port.in.dto.ConsultantRegionDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.devtalk.member.memberservice.global.success.SuccessCode.*;

@RestController
@RequestMapping("/api/consultant")
@RequiredArgsConstructor
public class ConsultantInfoApiController {
    private final ConsultantInfoUseCase consultantInfoUseCase;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/info")
    public SuccessResponse<ConsultantInfoRes> getInfo(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        ConsultantInfoRes infoRes = consultantInfoUseCase.getInfo(token);
        return new SuccessResponse<>(CONSULTANT_INFO_SUCCESS, infoRes);
    }

    @GetMapping("/info/type")
    public SuccessResponse<ConsultantTypeRes> getType(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        return new SuccessResponse<>();
    }

    @GetMapping("/info/category")
    public SuccessResponse<ConsultantCategoryDto> getCategory(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        ConsultantCategoryDto category = consultantInfoUseCase.getCategory();
        return new SuccessResponse<>();
    }

    @GetMapping("/info/region")
    public SuccessResponse<ConsultantRegionDto> getRegion() {
        return new SuccessResponse<>();
    }
}
