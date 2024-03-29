package com.devtalk.consultation.consultationservice.consultation.application;

import com.devtalk.consultation.consultationservice.consultation.application.port.in.SearchConsultationUseCase;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.ConsultationQueryableRepo;
//import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.ReviewPhotoQueryableRepo;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.Consultation;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.ConsultationCancellation;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.ReviewPhoto;
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
    //private final ReviewPhotoQueryableRepo reviewPhotoQueryableRepo;



    public List<ConsultationSearchRes> searchConsultationListByConsulter(Long consulterId) {
        List<Consultation> consultationList = consultationQueryableRepo.findAllByConsulterId(consulterId);

        return consultationList.stream().map(consultation -> ConsultationSearchRes.of(consultation)).toList();
    }

    @Override
    public List<ConsultationSearchRes> searchConsultationListByConsultant(Long consultantId) {
        List<Consultation> consultationList = consultationQueryableRepo.findAllByConsultantId(consultantId);

        return consultationList.stream().map(consultation -> ConsultationSearchRes.of(consultation)).toList();
    }

    @Override
    public ConsultationSearchRes searchConsultationDetailsByConsulter(Long consultationId, Long consulterId) {
        Consultation findConsultation = consultationQueryableRepo.findByIdWithConsulterId(consultationId, consulterId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_CONSULTATION));
        return ConsultationSearchRes.of(findConsultation);
    }
    @Override
    public ConsultationSearchRes searchConsultationDetailsByConsultant(Long consultationId, Long consultantId) {
        Consultation findConsultation = consultationQueryableRepo.findByIdWithConsultantId(consultationId, consultantId)
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

//    @Override
//    public List<ReviewSearchRes> searchReviewByConsultant(Long consultantId) {
//        return consultationQueryableRepo.findAllReviewByConsultantId(consultantId).stream()
//                .map(review -> ReviewSearchRes.of(review))
//                .toList();
//    }
//    @Override
//    public List<ReviewPhotoRes> searchPhotoByReview(Long reviewId) {
//        List<ReviewPhoto> reviewPhotoResList = reviewPhotoQueryableRepo.findAllPhotoByreviewId(reviewId);
//
//
//        return ReviewPhotoRes.of()
//    }
}
