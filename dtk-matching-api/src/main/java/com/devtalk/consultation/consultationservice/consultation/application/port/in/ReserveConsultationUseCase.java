package com.devtalk.consultation.consultationservice.consultation.application.port.in;

import static com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationReq.*;

public interface ReserveConsultationUseCase {
    void reserve(ReservationReq reservationReq);
}
