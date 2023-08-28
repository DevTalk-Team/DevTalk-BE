package com.devtalk.consultation.consultationservice.consultation.adapter.in.web;

import com.devtalk.consultation.consultationservice.consultation.application.port.in.CancelConsultationUseCase;
import com.devtalk.consultation.consultationservice.consultation.application.port.in.ReserveConsultationUseCase;
import com.devtalk.consultation.consultationservice.global.vo.SuccessCode;
import com.devtalk.consultation.consultationservice.global.vo.SuccessResponseWithoutResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.devtalk.consultation.consultationservice.consultation.adapter.in.web.dto.ConsultationInput.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ConsultationApiController {

    private final ReserveConsultationUseCase reserveUseCase;
    private final CancelConsultationUseCase cancelUseCase;

    @PostMapping("/v1/consulter/{consulterId}/consultations")
    public ResponseEntity<?> reserveConsultation(@RequestBody @Validated ReservationInput reservationInput,
                                                 @PathVariable Long consulterId) {
        reserveUseCase.reserve(reservationInput.toReq(consulterId));
        return SuccessResponseWithoutResult.toResponseEntity(SuccessCode.RESERVATION_SUCCESS);
    }

    @DeleteMapping("/v1/consulter/{consulterId}/consultations/{consultationId}")
    public ResponseEntity<?> cancelConsultationByConsulter(@RequestBody @Validated CancellationOfConsulterInput cancellationInput,
                                                 @PathVariable Long consulterId,
                                                @PathVariable Long consultationId) {
        cancelUseCase.cancelByConsulter(cancellationInput.toReq(consulterId, consultationId));
        return SuccessResponseWithoutResult.toResponseEntity(SuccessCode.RESERVATION_SUCCESS);
    }

    @DeleteMapping("/v1/consultant/{consultantId}/consultations/{consultationId}")
    public ResponseEntity<?> cancelConsultationByConsultant(@RequestBody @Validated CancellationOfConsultantInput cancellationInput,
                                                           @PathVariable Long consultantId,
                                                           @PathVariable Long consultationId) {
        cancelUseCase.cancelByConsultant(cancellationInput.toReq(consultantId, consultationId));
        return SuccessResponseWithoutResult.toResponseEntity(SuccessCode.RESERVATION_SUCCESS);
    }
}
