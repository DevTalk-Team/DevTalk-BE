package com.devtalk.consultation.consultationservice.consultation.application;

import com.devtalk.consultation.consultationservice.consultation.application.port.in.ReviewConsultationUseCase;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.ConsultationQueryableRepo;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.Consultation;
import com.devtalk.consultation.consultationservice.global.error.ErrorCode;
import com.devtalk.consultation.consultationservice.global.error.execption.NotFoundException;
import com.devtalk.consultation.consultationservice.global.vo.BaseFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationReq.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewConsultationService implements ReviewConsultationUseCase {

    private final ConsultationQueryableRepo consultationQueryableRepo;
    private final FileUploadService fileUploadService;

    @Transactional
    @Override
    public void reviewConsultation(ReviewReq reviewReq) {
        Consultation fidnConsultation = consultationQueryableRepo.findByIdWithConsulterId(reviewReq.getConsultationId(), reviewReq.getConsulterId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_CONSULTATION));

       // BaseFile photoFile = fileUploadService.uploadReviewPhoto(reviewReq.getPhoto());
//        fidnConsultation.writeReview(
//                reviewReq.getScore(), reviewReq.getContent(),
//                photoFile.getFileUrl(), photoFile.getOriginFileName(), photoFile.getStoredFileName(), LocalDateTime.now()
//        );
    }
}
