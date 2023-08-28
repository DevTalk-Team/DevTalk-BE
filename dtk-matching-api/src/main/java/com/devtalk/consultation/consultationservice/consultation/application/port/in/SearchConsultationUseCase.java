package com.devtalk.consultation.consultationservice.consultation.application.port.in;

import com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationRes;

import java.util.List;

public interface SearchConsultationUseCase {

    List<ConsultationRes.ConsultationSearchRes> searchConsultationBy(Long consulterId);
}
