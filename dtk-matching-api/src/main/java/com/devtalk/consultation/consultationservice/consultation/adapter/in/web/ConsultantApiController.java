package com.devtalk.consultation.consultationservice.consultation.adapter.in.web;

import com.devtalk.consultation.consultationservice.consultation.application.port.in.AcceptConsultationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ConsultantApiController {

    private final AcceptConsultationUseCase acceptConsultationUseCase;

    @PostMapping("/v1/consultants/{consultantId}/consultations/{consultationId}")
    public ResponseEntity<?> acceptConsultation(@PathVariable Long consultantId,
                                                @PathVariable Long consultationId) {
        return null;
    }
}
