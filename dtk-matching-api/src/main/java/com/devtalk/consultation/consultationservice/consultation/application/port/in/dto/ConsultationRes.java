package com.devtalk.consultation.consultationservice.consultation.application.port.in.dto;

import com.devtalk.consultation.consultationservice.consultation.domain.consultation.*;
import com.devtalk.consultation.consultationservice.global.vo.Money;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class ConsultationRes {

    @Builder(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ConsultationSearchRes {
        private Long id;
        private Long productId;
        private Long consulterId;
        private String consulterName;
        private Long consultantId;
        private String consultantName;
        private Long paymentId;
        private ConsultationDetails consultationDetails;
        private ProcessStatus status;
        private Integer cost;

        public static ConsultationSearchRes of(Consultation consultation) {
            return ConsultationSearchRes.builder()
                    .id(consultation.getId())
                    .productId(consultation.getProductId())
                    .consulterId(consultation.getConsulterId())
                    .consulterName(consultation.getConsulterName())
                    .consultantId(consultation.getConsultantId())
                    .consultantName(consultation.getConsultantName())
                    .paymentId(consultation.getPaymentId())
                    .consultationDetails(consultation.getConsultationDetails())
                    .status(consultation.getStatus())
                    .cost(consultation.getCost().getAmount().intValue())
                    .build();
        }
    }

    @AllArgsConstructor
    @Getter
    public static class CancellationReasonRes {
        private String reason;

        public static CancellationReasonRes of(ConsultationCancellation consultationCancellation) {
            return new CancellationReasonRes(consultationCancellation.getCanceledReason());
        }
    }

    @Builder(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ReviewSearchRes {
        private Long id;
        private String consulterName;
        private String consultantName;
        private Integer score;
        private String photoUrl;
        private String photoOriginName;
        private String photoStoredName;
        private String content;

        public static ReviewSearchRes of(Review review, ReviewPhoto reviewPhoto) {
            return ReviewSearchRes.builder()
                    .id(review.getId())
                    .consulterName(review.getConsulterName())
                    .consultantName(review.getConsultantName())
                    .score(review.getScore())
                    .photoUrl(reviewPhoto.getFileUrl())
                    .photoOriginName(reviewPhoto.getOriginFileName())
                    .photoStoredName(reviewPhoto.getStoredFileName())
                    .content(review.getContent())
                    .build();
        }
    }

    @Builder(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ReviewPhotoRes {
        private Long id;
        private Review review;
        private String photoUrl;
        private String photoOriginName;
        private String photoStoredName;

        public static ReviewPhotoRes of(ReviewPhoto reviewPhoto) {
            return ReviewPhotoRes.builder()
                    .id(reviewPhoto.getId())
                    .review(reviewPhoto.getReview())
                    .photoUrl(reviewPhoto.getFileUrl())
                    .photoOriginName(reviewPhoto.getOriginFileName())
                    .photoStoredName(reviewPhoto.getStoredFileName())
                    .build();
        }
    }
}
