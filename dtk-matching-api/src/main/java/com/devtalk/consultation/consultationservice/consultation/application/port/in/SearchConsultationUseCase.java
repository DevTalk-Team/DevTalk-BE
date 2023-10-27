package com.devtalk.consultation.consultationservice.consultation.application.port.in;

import java.util.List;

import static com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationRes.*;

public interface SearchConsultationUseCase {

    List<ConsultationSearchRes> searchConsultationListByConsulter(Long consulterId);
    List<ConsultationSearchRes> searchConsultationListByConsultant(Long consultantId);
    ConsultationSearchRes searchConsultationDetailsByConsulter(Long consultationId, Long consulterId);
    ConsultationSearchRes searchConsultationDetailsByConsultant(Long consultationId, Long consulterId);

    CancellationReasonRes searchCanceledConsultationDetailsByConsulter(Long consulterId, Long consultationId);
    CancellationReasonRes searchCanceledConsultationDetailsByConsultant(Long consultantId, Long consultationId);
    List<ReviewSearchRes>  searchReviewByConsultant(Long consultantId);
}
