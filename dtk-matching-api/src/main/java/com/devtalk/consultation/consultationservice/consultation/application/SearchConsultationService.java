package com.devtalk.consultation.consultationservice.consultation.application;

import com.devtalk.consultation.consultationservice.consultation.application.port.in.SearchConsultationUseCase;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.ConsultationQueryableRepo;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.Consultation;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.ConsultationCancellation;
import com.devtalk.consultation.consultationservice.global.error.execption.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationRes.*;
import static com.devtalk.consultation.consultationservice.global.error.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchConsultationService implements SearchConsultationUseCase {
    private final ConsultationQueryableRepo consultationQueryableRepo;

    public List<ConsultationSearchRes> searchConsultationBy(Long consulterId) {
        List<Consultation> consultationList = consultationQueryableRepo.findAllByConsulterId(consulterId);

        return consultationList.stream().map(consultation -> ConsultationSearchRes.of(consultation)).toList();
    }

    @Override
    public ConsultationSearchRes searchConsultationDetailsBy(Long consultationId, Long consulterId) {
        Consultation findConsultation = consultationQueryableRepo.findByIdWithConsulterId(consultationId, consulterId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_CONSULTATION));
        return ConsultationSearchRes.of(findConsultation);
    }

    @Override
    public CancellationReasonRes searchCanceledConsultationDetailsByConsulter(Long consulterId, Long consultationId) {
        ConsultationCancellation consultationCancellation = consultationQueryableRepo.findCancellationByConsultationIdAndConsulterId(consultationId, consulterId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_CONSULTATION));

        return CancellationReasonRes.of(consultationCancellation);
    }

    @Override
    public CancellationReasonRes searchCanceledConsultationDetailsByConsultant(Long consultantId, Long consultationId) {
        ConsultationCancellation consultationCancellation = consultationQueryableRepo.findCancellationByConsultationIdAndConsultantId(consultationId, consultantId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_CONSULTATION));

        return CancellationReasonRes.of(consultationCancellation);
    }

}
