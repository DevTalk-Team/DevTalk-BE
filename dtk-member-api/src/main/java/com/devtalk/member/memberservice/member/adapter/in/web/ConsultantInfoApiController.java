package com.devtalk.member.memberservice.member.adapter.in.web;

import com.devtalk.member.memberservice.global.security.MemberDetails;
import com.devtalk.member.memberservice.global.success.SuccessResponse;
import com.devtalk.member.memberservice.global.success.SuccessResponseNoResult;
import com.devtalk.member.memberservice.member.adapter.in.web.dto.ConsultantInput;
import com.devtalk.member.memberservice.member.adapter.out.producer.KafkaProducer;
import com.devtalk.member.memberservice.member.application.port.in.ConsultantInfoUseCase;
import com.devtalk.member.memberservice.member.application.port.out.dto.ConsultantRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.devtalk.member.memberservice.global.success.SuccessCode.*;

@Tag(name = "전문가 페이지", description = "메인, 상담 유형, 분야, 지역")
@Slf4j
@RestController
@RequestMapping("/member/consultant")
@RequiredArgsConstructor
public class ConsultantInfoApiController {
    private final ConsultantInfoUseCase consultantInfoUseCase;
    private final KafkaProducer kafkaProducer;

    @Operation(summary = "전문가 페이지 - 메인 조회", responses = {
            @ApiResponse(description = "성공", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    })
    @GetMapping("/info")
    public ResponseEntity<?> getInfo(@AuthenticationPrincipal MemberDetails memberDetails) {
        ConsultantRes.InfoRes res = consultantInfoUseCase.getInfo(memberDetails.getUsername());
        return SuccessResponse.toResponseEntity(CONSULTANT_INFO_SUCCESS, res);
    }

    @Operation(summary = "전문가 페이지 - 메인 수정", responses = {
            @ApiResponse(description = "성공", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    })
    @PostMapping("/info")
    public ResponseEntity<?> updateInfo(@RequestPart(value = "input") ConsultantInput.InfoInput input,
                                        @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                        @AuthenticationPrincipal MemberDetails memberDetails) {
        ConsultantRes.InfoRes res = consultantInfoUseCase.updateInfo(memberDetails.getUsername(), input.toReq(files));
        kafkaProducer.sendConsultantInfo(res.toEntity());
        return SuccessResponse.toResponseEntity(CONSULTANT_INFO_UPDATE_SUCCESS, res);
    }

    @Operation(summary = "전문가 페이지 - 상담 가능 유형(예: 커리어상담, 멘토섭외 ...) 조회", responses = {
            @ApiResponse(description = "성공", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    })
    @GetMapping("/info/type") // 상담 가능 유형 (예: 커리어 상담, 멘토섭외 ...)
    public ResponseEntity<?> getType(@AuthenticationPrincipal MemberDetails memberDetails) {
        List<String> res = consultantInfoUseCase.getType(memberDetails.getUsername());
        return SuccessResponse.toResponseEntity(CONSULTANT_INFO_TYPE_SUCCESS, res);
    }

    @Operation(summary = "전문가 페이지 - 상담 가능 유형(예: 커리어상담, 멘토섭외 ...) 수정", responses = {
            @ApiResponse(description = "성공", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    })
    @PutMapping("/info/type")
    public ResponseEntity<?> updateType(@RequestBody ConsultantInput.ListInput input,
                                        @AuthenticationPrincipal MemberDetails memberDetails) {
        consultantInfoUseCase.updateType(memberDetails.getUsername(), input);
        return SuccessResponseNoResult.toResponseEntity(CONSULTANT_INFO_TYPE_UPDATE_SUCCESS);
    }

    @Operation(summary = "전문가 페이지 - 상담 가능 카테고리(예: 웹, DB ...) 조회", responses = {
            @ApiResponse(description = "성공", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    })
    @GetMapping("/info/category") // 상담 가능 카테고리 (예: 웹, DB ...)
    public ResponseEntity<?> getCategory(@AuthenticationPrincipal MemberDetails memberDetails) {
        List<String> res = consultantInfoUseCase.getCategory(memberDetails.getUsername());
        return SuccessResponse.toResponseEntity(CONSULTANT_INFO_CATEGORY_SUCCESS, res);
    }

    @Operation(summary = "전문가 페이지 - 상담 가능 유형(예: 커리어상담, 멘토섭외 ...) 수정", responses = {
            @ApiResponse(description = "성공", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    })
    @PutMapping("/info/category")
    public ResponseEntity<?> updateCategory(@RequestBody ConsultantInput.ListInput input,
                                            @AuthenticationPrincipal MemberDetails memberDetails) {
        consultantInfoUseCase.updateCategory(memberDetails.getUsername(), input); // TODO input 검증
        return SuccessResponseNoResult.toResponseEntity(CONSULTANT_INFO_CATEGORY_UPDATE_SUCCESS);
    }

    @Operation(summary = "전문가 페이지 - 상담 가능 지역 조회", responses = {
            @ApiResponse(description = "성공", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    })
    @GetMapping("/info/region") // 상담 가능 지역
    public ResponseEntity<?> getRegion(@AuthenticationPrincipal MemberDetails memberDetails) {
        List<String> res = consultantInfoUseCase.getRegion(memberDetails.getUsername());
        return SuccessResponse.toResponseEntity(CONSULTANT_INFO_REGION_SUCCESS, res);
    }

    @Operation(summary = "전문가 페이지 - 상담 가능 지역 수정", responses = {
            @ApiResponse(description = "성공", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    })
    @PutMapping("/info/region")
    public ResponseEntity<?> updateRegion(@RequestBody ConsultantInput.ListInput input,
                                          @AuthenticationPrincipal MemberDetails memberDetails) {
        consultantInfoUseCase.updateRegion(memberDetails.getUsername(), input); // TODO input 검증
        return SuccessResponseNoResult.toResponseEntity(CONSULTANT_INFO_REGION_UPDATE_SUCCESS);
    }

    @Operation(summary = "상담 - 조건 맞는 전문가 조회", responses = {
            @ApiResponse(description = "성공", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    })
    @PostMapping
    ResponseEntity<?> getConsultant(@RequestBody ConsultantInput.ConsultationInput input) {
        List<ConsultantRes.ConsultationRes> res = consultantInfoUseCase.findConsultantForConsultation(input.toReq());
        return SuccessResponse.toResponseEntity(FIND_CONSULTANT_SUCCESS, res);
    }

    @Operation(summary = "상담 - 전문가 별점 평가", responses = {
            @ApiResponse(description = "성공", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponseNoResult.class)))
    })
    @GetMapping("/{consultantId}/rate")
    ResponseEntity<?> rate(@PathVariable(value = "consultantId") Long id,
                           @RequestParam(value = "star") int star) {
        consultantInfoUseCase.rate(id, star);
        return SuccessResponseNoResult.toResponseEntity(CONSULTANT_RATE_SUCCESS);
    }
}
