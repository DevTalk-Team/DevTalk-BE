package com.devtalk.consultation.consultationservice.consultation.adapter.in.web;

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

    @PostMapping("/v1/consultations")
    public ResponseEntity<?> reserveConsultation(@RequestBody @Validated ReservationInput reservationInput) {

    }
}
