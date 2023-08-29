package com.devtalk.consultation.consultationservice.consultation.application.port.in;

import com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationReq;

import static com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationReq.*;

public interface ModifyConsultationUseCase {
    Long modifyConsultation(ConsultationModificationReq modificationReq);
}
