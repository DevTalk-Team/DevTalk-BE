package com.devtalk.consultation.consultationservice.consultation.application.port.in.dto;

import com.devtalk.consultation.consultationservice.consultation.domain.consultation.ProcessMean;
import jakarta.validation.constraints.Future;
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
        private Long consultantId;
        private Long productId;
        private ProcessMean processMean;
        private String largeArea;
        private String detailArea;
        private LocalDateTime reservationAT;
        private String content;

        @Builder.Default
        private List<MultipartFile> attachedFileList = new ArrayList<>();
        private Integer cost;
    }
}
