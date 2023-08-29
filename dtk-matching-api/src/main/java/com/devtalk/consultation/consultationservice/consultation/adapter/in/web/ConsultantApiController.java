package com.devtalk.consultation.consultationservice.consultation.adapter.in.web;

import com.devtalk.consultation.consultationservice.consultation.adapter.in.web.dto.ConsultationInput;
import com.devtalk.consultation.consultationservice.consultation.application.port.in.AcceptConsultationUseCase;
import com.devtalk.consultation.consultationservice.consultation.application.port.in.CancelConsultationUseCase;
import com.devtalk.consultation.consultationservice.consultation.application.port.in.SearchConsultationUseCase;
import com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationRes;
import com.devtalk.consultation.consultationservice.global.vo.SuccessCode;
import com.devtalk.consultation.consultationservice.global.vo.SuccessResponse;
import com.devtalk.consultation.consultationservice.global.vo.SuccessResponseWithoutResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationRes.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ConsultantApiController {

    private final AcceptConsultationUseCase acceptConsultationUseCase;
    private final CancelConsultationUseCase cancelUseCase;
    private final SearchConsultationUseCase searchUseCase;

    @DeleteMapping("/v1/consultants/{consultantId}/consultations/{consultationId}")
    public ResponseEntity<?> cancelConsultationByConsultant(@RequestBody @Validated ConsultationInput.CancellationOfConsultantInput cancellationInput,
                                                            @PathVariable Long consultantId,
                                                            @PathVariable Long consultationId) {
        cancelUseCase.cancelByConsultant(cancellationInput.toReq(consultantId, consultationId));
        return SuccessResponseWithoutResult.toResponseEntity(SuccessCode.CONSULTATION_CONSULTANT_CANCEL_SUCCESS);
    }

    @PostMapping("/v1/consultants/{consultantId}/consultations/{consultationId}")
    public ResponseEntity<?> acceptConsultation(@PathVariable Long consultantId,
                                                @PathVariable Long consultationId) {
        acceptConsultationUseCase.acceptConsultation(consultantId, consultationId);
        return SuccessResponseWithoutResult.toResponseEntity(SuccessCode.CONSULTANT_CONSULTATION_ACCEPT_SUCCESS);
    }

    @GetMapping("/v1/consultants/{consultantId}/canceled-consultations/{consultationId}")
    public ResponseEntity<SuccessResponse> searchCanceledConsultationByConsultant(@PathVariable Long consultantId,
                                                                                  @PathVariable Long consultationId) {
        return SuccessResponse.toResponseEntity(SuccessCode.CONSULTER_CANCELED_CONSULTATION_SEARCH_SUCCESS,
                searchUseCase.searchCanceledConsultationDetailsByConsultant(consultantId, consultationId));
    }

    @GetMapping("/v1/consultants/{consultantId}/consultations")
    public ResponseEntity<SuccessResponse> searchConsultationByConsultant(@PathVariable Long consultantId) {
        return SuccessResponse.toResponseEntity(SuccessCode.CONSULTER_CANCELED_CONSULTATION_SEARCH_SUCCESS,
                searchUseCase.searchConsultationListByConsultant(consultantId));
    }

    @GetMapping("/v1/consultants/{consultantId}/reviews")
    public ResponseEntity<SuccessResponse> searchReviewsByConsultant(@PathVariable Long consultantId) {
        return SuccessResponse.toResponseEntity(SuccessCode.CONSULTER_CANCELED_CONSULTATION_SEARCH_SUCCESS,
                searchUseCase.searchConsultationListByConsultant(consultantId));
    }
}
