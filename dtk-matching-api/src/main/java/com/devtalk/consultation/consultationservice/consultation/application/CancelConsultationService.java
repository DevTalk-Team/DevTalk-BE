package com.devtalk.consultation.consultationservice.consultation.application;

import com.devtalk.consultation.consultationservice.consultation.application.port.in.CancelConsultationUseCase;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.ConsultationQueryableRepo;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.Consultation;
import com.devtalk.consultation.consultationservice.global.error.ErrorCode;
import com.devtalk.consultation.consultationservice.global.error.execption.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationReq.*;
import static com.devtalk.consultation.consultationservice.global.error.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CancelConsultationService implements CancelConsultationUseCase {

    private final ConsultationQueryableRepo consultationQueryableRepo;


    // TODO: 결제가 이미 된 매칭이라면
    // 1. 결제 서비스에 결제 취소요청
    // 2. 상품 서비스에 상품 취소요청

    @Override
    @Transactional
    public void cancelByConsulter(CancellationOfConsulterReq cancellationReq) {
        Consultation consultation = consultationQueryableRepo.findByIdWithConsulterId(cancellationReq.getConsultationId(), cancellationReq.getConsulterId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_CONSULTATION));

        consultation.cancelByConsulter(cancellationReq.getReason());
        //TODO: 상담취소 아이템 생성

    }

    @Override
    @Transactional
    public void cancelByConsultant(CancellationOfConsultantReq cancellationReq) {
        //TODO: 상담사가 상담취소를 하는데 케이스가 2가지가 있음
        // 1. 그냥 처음부터 요청들어오면 거절하는것
        // 2. 승인 후에 취소하는 것 (결제전, 결제후)

        Consultation consultation = consultationQueryableRepo.findByIdWithConsultantId(cancellationReq.getConsultationId(), cancellationReq.getConsultantId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_CONSULTATION));

        consultation.cancelByConsultant(cancellationReq.getReason());

    }
}