package com.devtalk.consultation.consultationservice.consultation.adapter.in.web;

import com.devtalk.consultation.consultationservice.consultation.application.port.in.ReserveUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.devtalk.consultation.consultationservice.consultation.adapter.in.web.dto.ConsultationInput.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ConsultationApiController {

    private final ReserveUseCase reserveUseCase;

    @PostMapping("/v1/consultations")
    public ResponseEntity<?> reserveConsultation(@RequestBody @Validated ReservationInput reservationInput) {
        reserveUseCase.reserve(reservationInput.toReq());
        return ResponseEntity.ok().build();
    }
}
