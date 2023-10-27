package com.devtalk.consultation.consultationservice.consultation.adapter.in.web;

import com.devtalk.consultation.consultationservice.consultation.application.port.in.*;
import com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationReq;
import com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationRes;
import com.devtalk.consultation.consultationservice.global.vo.SuccessCode;
import com.devtalk.consultation.consultationservice.global.vo.SuccessResponse;
import com.devtalk.consultation.consultationservice.global.vo.SuccessResponseWithoutResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.devtalk.consultation.consultationservice.consultation.adapter.in.web.dto.ConsultationInput.*;
import static com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationRes.*;

@Tag(name = "매칭서비스-내담자", description = "상담 승인, 거절, 취소 내역 조회, 전체 조회, 리뷰 조회")
@EnableDiscoveryClient
@RestController
@Slf4j
@RequestMapping("/matching")
@RequiredArgsConstructor
public class ConsulterApiController {

    private final ReserveConsultationUseCase reserveUseCase;
    private final CancelConsultationUseCase cancelUseCase;
    private final ModifyConsultationUseCase modifyUseCase;
    private final SearchConsultationUseCase searchUseCase;
    private final ReviewConsultationUseCase reviewUseCase;
    private final AuthUseCase authUseCase;
    private final Environment env;


    @Operation(summary = "내담자 - 상담 예약", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponseWithoutResult.class)))
    })
    @PostMapping(path = "/consulters/reserve/consultations", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> reserveConsultation(@RequestParam("reservationJson") String reservationJson,
                                                 @RequestPart(value = "attachedFiles", required = false) List<MultipartFile> attachedFiles,
                                                 @RequestHeader(value = "User-Email") String userEmail) throws JsonProcessingException {
        log.info("User-Eamil : {}", userEmail);
        Long consulterId = authUseCase.auth(userEmail);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        ReservationInput reservationInput = mapper.readValue(reservationJson, ReservationInput.class);

        ConsultationReq.ReservationReq reservationReq = reservationInput.toReq(consulterId);
        reserveUseCase.reserve(reservationReq, attachedFiles);
        return SuccessResponseWithoutResult.toResponseEntity(SuccessCode.CONSULTATION_RESERVATION_SUCCESS);
    }




    @Operation(summary = "내담자 - 상담 취소", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponseWithoutResult.class)))
    })
    @DeleteMapping("/consulters/cancle/consultations/{consultationId}")
    public ResponseEntity<?> cancelConsultationByConsulter(@RequestBody @Validated CancellationOfConsulterInput cancellationInput,
                                                           @RequestHeader(value = "User-Email") String userEmail,
                                                           @PathVariable Long consultationId) {
        log.info("User-Eamil : {}", userEmail);
        Long consulterId = authUseCase.auth(userEmail);
        cancelUseCase.cancelByConsulter(cancellationInput.toReq(consulterId, consultationId));
        return SuccessResponseWithoutResult.toResponseEntity(SuccessCode.CONSULTATION_CONSULTER_CANCEL_SUCCESS);
    }

    @Operation(summary = "내담자 - 상담 내용 수정 (첨부 파일이나 내용)", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponseWithoutResult.class)))
    })
    @PatchMapping("/consulters/update/consultations/{consultationId}")
    public ResponseEntity<?> modifyConsultationContents(@RequestBody @Validated ConsultationModificationInput consultationModificationInput,
                                                        @RequestHeader(value = "User-Email") String userEmail,
                                                        @PathVariable Long consultationId) {
        log.info("User-Eamil : {}", userEmail);
        Long consulterId = authUseCase.auth(userEmail);
        modifyUseCase.modifyConsultation(consultationModificationInput.toReq(consulterId, consultationId));
        return SuccessResponseWithoutResult.toResponseEntity(SuccessCode.CONSULTATION_MODIFICATION_SUCCESS);
    }

    @Operation(summary = "내담자 - 상담 내역 전체 조회 (모든 상태)", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = ConsultationSearchRes.class)))
    })
    @GetMapping("/consulters/searchAllConsultation")
    public ResponseEntity<SuccessResponse> searchConsultationByConsulter(@RequestHeader(value = "User-Email") String userEmail) {
        log.info("User-Eamil : {}", userEmail);
        Long consulterId = authUseCase.auth(userEmail);
        return SuccessResponse.toResponseEntity(SuccessCode.CONSULTER_CONSULTATION_SEARCH_SUCCESS,
                searchUseCase.searchConsultationListByConsulter(consulterId));
    }

    @Operation(summary = "내담자 - 상담 1개 세부 내용 조회", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConsultationSearchRes.class)))
    })
    @GetMapping("/consulters/searchOneConsultation/consultation/{consultationId}")
    public ResponseEntity<SuccessResponse> searchConsultationDetailsByConsulter(@RequestHeader(value = "User-Email") String userEmail,
                                                                                @PathVariable Long consultationId) {
        log.info("User-Eamil : {}", userEmail);
        Long consulterId = authUseCase.auth(userEmail);
        return SuccessResponse.toResponseEntity(SuccessCode.CONSULTER_CONSULTATION_DETAIL_SEARCH_SUCCESS,
                searchUseCase.searchConsultationDetailsByConsulter(consultationId, consulterId));
    }

    @Operation(summary = "내담자 - 취소된 상담 내역 조회", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CancellationReasonRes.class)))
    })
    @GetMapping("/consulters/canceled-consultations/{consultationId}")
    public ResponseEntity<SuccessResponse> searchCanceledConsultationByConsulter(@RequestHeader(value = "User-Email") String userEmail,
                                                                                 @PathVariable Long consultationId) {
        log.info("User-Eamil : {}", userEmail);
        Long consulterId = authUseCase.auth(userEmail);
        return SuccessResponse.toResponseEntity(SuccessCode.CONSULTER_CANCELED_CONSULTATION_SEARCH_SUCCESS,
                searchUseCase.searchCanceledConsultationDetailsByConsulter(consulterId, consultationId));
    }

    @Operation(summary = "내담자 - 리뷰 작성", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponseWithoutResult.class)))
    })
    @PostMapping("/consulter/reviews/consultations/{consultationId}")
    public ResponseEntity<?> writeReview(@RequestBody @Validated ReviewInput reviewInput,
                                         @RequestHeader(value = "User-Email") String userEmail,
                                         @PathVariable Long consultationId) {
        log.info("User-Eamil : {}", userEmail);
        Long consulterId = authUseCase.auth(userEmail);
        reviewUseCase.reviewConsultation(reviewInput.toReq(consulterId, consultationId));
        return SuccessResponseWithoutResult.toResponseEntity(SuccessCode.CONSULTATION_REVIEW_WRITE_SUCCESS);
    }
}
