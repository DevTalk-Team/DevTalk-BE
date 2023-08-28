package com.devtalk.consultation.consultationservice.consultation.application.port.in.dto;

import com.devtalk.consultation.consultationservice.consultation.domain.consultation.AttachedFile;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.Consultation;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.ConsultationDetails;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.ProcessMean;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConsultationReq {
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ReservationReq {

        private Long consulterId;
        private String consulterName;
        private Long consultantId;
        private String consultantName;
        private Long productId;
        private ProcessMean processMean;
        private String largeArea;
        private String detailArea;
        private LocalDateTime reservationAT;
        private String content;

        @Builder.Default
        private List<MultipartFile> attachedFileList = new ArrayList<>();
        private Integer cost;

        public Consultation toEntity(List<AttachedFile> attachedFileList) {
            ConsultationDetails consultationDetails = ConsultationDetails.builder()
                    .processMean(processMean)
                    .largeArea(largeArea)
                    .detailArea(detailArea)
                    .reservationAT(reservationAT)
                    .attachedFileList(attachedFileList)
                    .content(content)
                    .build();

            return Consultation.createConsultation(consulterId, consulterName, consultantId, consultantName, productId, consultationDetails, cost);
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class CancellationOfConsulterReq {
        private Long consulterId;
        private Long consultationId;
        private String reason;
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class CancellationOfConsultantReq {
        private Long consultantId;
        private Long consultationId;
        private String reason;
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ConsultationModificationReq {
        private Long consulterId;
        private Long consultationId;
        private String content;
        private List<MultipartFile> attachedFileList = new ArrayList<>();
    }
}
