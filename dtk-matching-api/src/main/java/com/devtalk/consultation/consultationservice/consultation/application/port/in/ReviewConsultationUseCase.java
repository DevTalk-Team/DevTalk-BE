package com.devtalk.consultation.consultationservice.consultation.application.port.in;

import com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationReq;

import static com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationReq.*;

public interface ReviewConsultationUseCase {

    void reviewConsultation(ReviewReq reviewReq);
}
