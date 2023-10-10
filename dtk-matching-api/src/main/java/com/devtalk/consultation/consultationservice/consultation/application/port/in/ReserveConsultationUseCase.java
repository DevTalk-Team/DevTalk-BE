package com.devtalk.consultation.consultationservice.consultation.application.port.in;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationReq.*;

public interface ReserveConsultationUseCase {
    public void reserve(ReservationReq reservationReq, List<MultipartFile> attachedFiles);
}
