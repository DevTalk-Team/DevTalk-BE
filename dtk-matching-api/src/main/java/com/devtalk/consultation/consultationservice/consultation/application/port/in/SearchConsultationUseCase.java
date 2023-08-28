package com.devtalk.consultation.consultationservice.consultation.application.port.in;

import com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationRes;

import java.util.List;

import static com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationRes.*;

public interface SearchConsultationUseCase {

    List<ConsultationSearchRes> searchConsultationBy(Long consulterId);
    ConsultationSearchRes searchConsultationDetailsBy(Long consultationId, Long consulterId);
    CancellationReasonRes searchCanceledConsultationDetailsByConsulter(Long consulterId, Long consultationId);
    CancellationReasonRes searchCanceledConsultationDetailsByConsultant(Long consultantId, Long consultationId);
}
