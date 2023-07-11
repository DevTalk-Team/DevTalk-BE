package com.devtalk.consultation.consultationservice.consultation.application;

import com.devtalk.consultation.consultationservice.consultation.application.port.in.ReserveUseCase;
import com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationReq;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.client.ProductServiceClient;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.client.dto.ProductRes;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.ConsultationQueryableRepo;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.ConsultationRepo;
import com.devtalk.consultation.consultationservice.consultation.application.validator.ConsultationValidator;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.Consultation;
import com.devtalk.consultation.consultationservice.global.error.ErrorCode;
import com.devtalk.consultation.consultationservice.global.error.execption.BusinessRuleException;
import com.devtalk.consultation.consultationservice.global.util.FileValidatorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationReq.*;
import static com.devtalk.consultation.consultationservice.consultation.application.port.out.client.dto.ProductRes.*;
import static com.devtalk.consultation.consultationservice.global.error.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ReserveService implements ReserveUseCase {

    private final ConsultationValidator consultationValidator;
    private final ConsultationRepo consultationRepo;
    private final ConsultationQueryableRepo consultationQueryableRepo;

    //findByConsulterId의 결과가 Null이면 Consultation을 생성
    public void reserve(ReservationReq reservationReq) {
        consultationValidator.validate(reservationReq);

        Consultation consultation = consultationQueryableRepo.findByConsulterId(reservationReq.getConsultantId())
                .orElseThrow(() -> new BusinessRuleException(NOT_FOUND_CONSULTATION));


        consultation.reserve(reservationReq.getConsultantId(), reservationReq.getConsultantName(), reservationReq.getConsultantEmail());
    }


}
