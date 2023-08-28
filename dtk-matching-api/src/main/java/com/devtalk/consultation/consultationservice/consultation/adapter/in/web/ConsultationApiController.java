package com.devtalk.consultation.consultationservice.consultation.adapter.in.web;

import com.devtalk.consultation.consultationservice.consultation.application.port.in.CancelConsultationUseCase;
import com.devtalk.consultation.consultationservice.consultation.application.port.in.ModifyConsultationUseCase;
import com.devtalk.consultation.consultationservice.consultation.application.port.in.ReserveConsultationUseCase;
import com.devtalk.consultation.consultationservice.consultation.application.port.in.SearchConsultationUseCase;
import com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationRes;
import com.devtalk.consultation.consultationservice.global.vo.SuccessCode;
import com.devtalk.consultation.consultationservice.global.vo.SuccessResponse;
import com.devtalk.consultation.consultationservice.global.vo.SuccessResponseWithoutResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.devtalk.consultation.consultationservice.consultation.adapter.in.web.dto.ConsultationInput.*;
import static com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationRes.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ConsultationApiController {

    private final ReserveConsultationUseCase reserveUseCase;
    private final CancelConsultationUseCase cancelUseCase;
    private final ModifyConsultationUseCase modifyUseCase;
    private final SearchConsultationUseCase searchUseCase;

    @PostMapping("/v1/consulter/{consulterId}/consultations")
    public ResponseEntity<?> reserveConsultation(@RequestBody @Validated ReservationInput reservationInput,
                                                 @PathVariable Long consulterId) {
        reserveUseCase.reserve(reservationInput.toReq(consulterId));
        return SuccessResponseWithoutResult.toResponseEntity(SuccessCode.CONSULTATION_RESERVATION_SUCCESS);
    }

    @DeleteMapping("/v1/consulter/{consulterId}/consultations/{consultationId}")
    public ResponseEntity<?> cancelConsultationByConsulter(@RequestBody @Validated CancellationOfConsulterInput cancellationInput,
                                                 @PathVariable Long consulterId,
                                                @PathVariable Long consultationId) {
        cancelUseCase.cancelByConsulter(cancellationInput.toReq(consulterId, consultationId));
        return SuccessResponseWithoutResult.toResponseEntity(SuccessCode.CONSULTATION_CONSULTER_CANCEL_SUCCESS);
    }

    @DeleteMapping("/v1/consultant/{consultantId}/consultations/{consultationId}")
    public ResponseEntity<?> cancelConsultationByConsultant(@RequestBody @Validated CancellationOfConsultantInput cancellationInput,
                                                           @PathVariable Long consultantId,
                                                           @PathVariable Long consultationId) {
        cancelUseCase.cancelByConsultant(cancellationInput.toReq(consultantId, consultationId));
        return SuccessResponseWithoutResult.toResponseEntity(SuccessCode.CONSULTATION_CONSULTANT_CANCEL_SUCCESS);
    }

    @PatchMapping("/v1/consulter/{consulterId}/consultations/{consultationId}")
    public ResponseEntity<?> modifyConsultationContents(@RequestBody @Validated ConsultationModificationInput consultationModificationInput,
                                                        @PathVariable Long consulterId,
                                                        @PathVariable Long consultationId) {
        modifyUseCase.modifyConsultation(consultationModificationInput.toReq(consulterId, consultationId));
        return SuccessResponseWithoutResult.toResponseEntity(SuccessCode.CONSULTATION_MODIFICATION_SUCCESS);
    }

    @GetMapping("/v1/consulter/{consulterId}/consultations")
    public ResponseEntity<SuccessResponse> searchConsultationByConsulter(@PathVariable Long consulterId) {
        return SuccessResponse.toResponseEntity(SuccessCode.CONSULTER_CONSULTATION_SEARCH_SUCCESS, searchUseCase.searchConsultationBy(consulterId));
    }

    @GetMapping("/v1/consulter/{consulterId}/consultations/{consultationId}")
    public ResponseEntity<SuccessResponse> searchConsultationDetailsByConsulter(@PathVariable Long consulterId,
                                                                         @PathVariable Long consultationId) {
        return SuccessResponse.toResponseEntity(SuccessCode.CONSULTER_CONSULTATION_DETAIL_SEARCH_SUCCESS, searchUseCase.searchConsultationDetailsBy(consulterId, consultationId));
    }

    @GetMapping("/v1/consulter/{consulterId}/canceled-consultations/{consultationId}")
    public ResponseEntity<SuccessResponse> searchCanceledConsultationByConsulter(@PathVariable Long consulterId,
                                                                                 @PathVariable Long consultationId) {
        return SuccessResponse.toResponseEntity(SuccessCode.CONSULTER_CANCELED_CONSULTATION_SEARCH_SUCCESS, searchUseCase.searchCanceledConsultationDetailsBy(consulterId, consultationId));
    }
}
