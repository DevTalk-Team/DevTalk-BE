package com.devtalk.consultation.consultationservice.consultation.application.port.in;

import com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationRes;

import java.util.List;

import static com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationRes.*;

public interface SearchConsultationUseCase {

    List<ConsultationSearchRes> searchConsultationBy(Long consulterId);
    ConsultationSearchRes searchConsultationDetailsBy(Long consultationId, Long consulterId);
    CancellationReasonRes searchCanceledConsultationDetailsBy(Long consulterId, Long consultationId);
}
