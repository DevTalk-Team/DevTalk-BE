//package com.devtalk.consultation.consultationservice.consultation.application;
//
//import com.devtalk.consultation.consultationservice.consultation.application.port.in.FileUploadUseCase;
//import com.devtalk.consultation.consultationservice.consultation.application.port.in.ReviewConsultationUseCase;
//import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.ConsultationQueryableRepo;
////import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.ReviewRepo;
//import com.devtalk.consultation.consultationservice.consultation.domain.consultation.Consultation;
//import com.devtalk.consultation.consultationservice.consultation.domain.consultation.Review;
//import com.devtalk.consultation.consultationservice.consultation.domain.consultation.ReviewPhoto;
//import com.devtalk.consultation.consultationservice.global.error.ErrorCode;
//import com.devtalk.consultation.consultationservice.global.error.execption.NotFoundException;
//import com.devtalk.consultation.consultationservice.global.vo.BaseFile;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationReq.*;
//
//@Service
//@Transactional(readOnly = true)
//@RequiredArgsConstructor
//public class ReviewConsultationService implements ReviewConsultationUseCase {
//
//    private final ConsultationQueryableRepo consultationQueryableRepo;
//    private final FileUploadUseCase fileUploadUseCase;
//    //private final ReviewRepo reviewRepo;
//
//    @Transactional
//    @Override
//    public void reviewConsultation(ReviewReq reviewReq, List<MultipartFile> files) {
//        Consultation fidnConsultation = consultationQueryableRepo.findByIdWithConsulterId(reviewReq.getConsultationId(), reviewReq.getConsulterId())
//                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_CONSULTATION));
//
//        Review review = Review.createReview(fidnConsultation.getConsulterId(), fidnConsultation.getConsulterName(), fidnConsultation.getConsultantId(),
//                fidnConsultation.getConsultantName(), reviewReq.getScore(), reviewReq.getContent());
//
//        if (files != null) {
//            uploadReviewPhoto(review, files);
//        }
//    }
//
//        private void uploadReviewPhoto(Review review, List<MultipartFile> files) {
//            List<BaseFile> baseFiles = fileUploadUseCase.uploadReviewPhoto(files);
//
//            baseFiles.forEach(baseFile ->
//                    reviewRepo.save(
//                            ReviewPhoto.uploadReviewPhoto(
//                                    review,
//                                    baseFile.getFileUrl(),
//                                    baseFile.getOriginFileName(),
//                                    baseFile.getStoredFileName()
//                            ))
//            );
//    }
//}
