package com.devtalk.consultation.consultationservice.consultation.adapter.in.web;

import com.devtalk.consultation.consultationservice.consultation.application.port.in.ReserveUseCase;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.devtalk.consultation.consultationservice.consultation.adapter.in.web.dto.ConsultationInput.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ConsultationApiController {

    private final ReserveUseCase reserveUseCase;

    @PostMapping("/v1/consulter/{consulterId}/consultations")
    public ResponseEntity<?> reserveConsultation(@RequestBody @Validated ReservationInput reservationInput,
                                                 @PathVariable Long consulterId) {
        reserveUseCase.reserve(reservationInput.toReq(consulterId));
        return ResponseEntity.ok().build();
    }
}
