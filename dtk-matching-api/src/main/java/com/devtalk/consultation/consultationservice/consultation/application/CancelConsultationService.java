package com.devtalk.consultation.consultationservice.consultation.application;

import com.devtalk.consultation.consultationservice.consultation.application.port.in.CancelConsultationUseCase;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.ConsultationQueryableRepo;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.Consultation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationReq.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CancelConsultationService implements CancelConsultationUseCase {

    private final ConsultationQueryableRepo consultationQueryableRepo;

    @Override
    @Transactional
    public void cancelByConsulter(CancellationOfConsulterReq cancellationReq) {
        Consultation consultation = consultationQueryableRepo.findByIdWithConsulterId(cancellationReq.getConsultationId(), cancellationReq.getConsulterId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상담입니다."));

        consultation.cancelByConsulter(cancellationReq.getReason());
        //TODO: 상담취소 아이템 생성

    }

    @Override
    @Transactional
    public void cancelByConsultant(CancellationOfConsultantReq cancellationReq) {
        //TODO: 상담사가 상담취소를 하는데 케이스가 2가지가 있음
        // 1. 그냥 처음부터 요청들어오면 거절하는것
        // 2. 승인 후에 취소하는 것 (결제전, 결제후)

        Consultation consultation = consultationQueryableRepo.findByIdWithConsulterId(cancellationReq.getConsultationId(), cancellationReq.getConsultantId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상담입니다."));

        consultation.cancelByConsultant(cancellationReq.getReason());

    }
}