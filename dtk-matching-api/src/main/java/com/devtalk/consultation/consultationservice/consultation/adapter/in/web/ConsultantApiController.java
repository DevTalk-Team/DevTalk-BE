package com.devtalk.consultation.consultationservice.consultation.adapter.in.web;

import com.devtalk.consultation.consultationservice.consultation.adapter.in.web.dto.ConsultationInput;
import com.devtalk.consultation.consultationservice.consultation.application.port.in.AcceptConsultationUseCase;
import com.devtalk.consultation.consultationservice.consultation.application.port.in.CancelConsultationUseCase;
import com.devtalk.consultation.consultationservice.consultation.application.port.in.SearchConsultationUseCase;
import com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationRes;
import com.devtalk.consultation.consultationservice.global.vo.SuccessCode;
import com.devtalk.consultation.consultationservice.global.vo.SuccessResponse;
import com.devtalk.consultation.consultationservice.global.vo.SuccessResponseWithoutResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.devtalk.consultation.consultationservice.consultation.adapter.in.web.dto.ConsultationInput.*;
import static com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationRes.*;

@Tag(name = "매칭서비스-상담사", description = "상담 승인, 거절, 취소 내역 조회, 전체 조회, 리뷰 조회")
@RestController
@RequestMapping("/matching")
@RequiredArgsConstructor
public class ConsultantApiController {

    private final AcceptConsultationUseCase acceptConsultationUseCase;
    private final CancelConsultationUseCase cancelUseCase;
    private final SearchConsultationUseCase searchUseCase;
    private final Environment env;


    // 상담 거절, 결제 전 취소, 결제 후 취소는 모두 이 api로
    @GetMapping("/consultants/health_check")
    public String status() {
        return String.format("It's Working in User Service"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")
                + ", port(server.port)=" + env.getProperty("server.port")
                + ", token secret=" + env.getProperty("token.secret")
                + ", token expiration time=" + env.getProperty("token.expiration_time"));
    }


    @Operation(summary = "상담사 - 상담 취소 및 거절", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponseWithoutResult.class))),
    })
    @DeleteMapping("/consultants/{consultantId}/consultations/{consultationId}")
    public ResponseEntity<?> cancelConsultationByConsultant(@RequestBody @Validated CancellationOfConsultantInput cancellationInput,
                                                            @PathVariable Long consultantId,
                                                            @PathVariable Long consultationId) {
        cancelUseCase.cancelByConsultant(cancellationInput.toReq(consultantId, consultationId));
        return SuccessResponseWithoutResult.toResponseEntity(SuccessCode.CONSULTATION_CONSULTANT_CANCEL_SUCCESS);
    }

    @Operation(summary = "상담사 - 상담 승인", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponseWithoutResult.class)))
    })
    @PostMapping("/consultants/{consultantId}/consultations/{consultationId}")
    public ResponseEntity<?> acceptConsultation(@PathVariable Long consultantId,
                                                @PathVariable Long consultationId) {
        acceptConsultationUseCase.acceptConsultation(consultantId, consultationId);
        return SuccessResponseWithoutResult.toResponseEntity(SuccessCode.CONSULTANT_CONSULTATION_ACCEPT_SUCCESS);
    }

    @Operation(summary = "상담사 - 취소된 상담 내역 조회", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CancellationReasonRes.class)))
    })
    @GetMapping("/consultants/{consultantId}/canceled-consultations/{consultationId}")
    public ResponseEntity<SuccessResponse> searchCanceledConsultationByConsultant(@PathVariable Long consultantId,
                                                                                  @PathVariable Long consultationId) {
        return SuccessResponse.toResponseEntity(SuccessCode.CONSULTER_CANCELED_CONSULTATION_SEARCH_SUCCESS,
                searchUseCase.searchCanceledConsultationDetailsByConsultant(consultantId, consultationId));
    }

    @Operation(summary = "상담사 - 상담 전체 내역 조회(모든 상태)", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = ConsultationSearchRes.class)))
    })
    @GetMapping("/consultants/{consultantId}/consultations")
    public ResponseEntity<SuccessResponse> searchConsultationByConsultant(@PathVariable Long consultantId) {
        return SuccessResponse.toResponseEntity(SuccessCode.CONSULTER_CANCELED_CONSULTATION_SEARCH_SUCCESS,
                searchUseCase.searchConsultationListByConsultant(consultantId));
    }

    @Operation(summary = "상담사 - 리뷰 전체 조회", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = ReviewSearchRes.class)))
    })
    @GetMapping("/consultants/{consultantId}/reviews")
    public ResponseEntity<SuccessResponse> searchReviewsByConsultant(@PathVariable Long consultantId) {
        return SuccessResponse.toResponseEntity(SuccessCode.CONSULTANT_REVIEW_SEARCH_SUCCESS,
                searchUseCase.searchReviewByConsultant(consultantId));
    }
}
