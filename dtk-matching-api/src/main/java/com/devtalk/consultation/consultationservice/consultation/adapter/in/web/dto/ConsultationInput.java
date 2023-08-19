package com.devtalk.consultation.consultationservice.consultation.adapter.in.web.dto;

import com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationReq;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.ProcessMean;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationReq.*;

public class ConsultationInput {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ReservationInput {

        @NotBlank(message = "상담자 ID는 null이 될 수 없습니다.")
        private Long consultantId;

        @NotBlank(message = "상담자 이름은 null이 될 수 없습니다.")
        private String consultantName;

        @NotBlank(message = "내담자 이름은 null이 될 수 없습니다.")
        private String consulterName;

        @NotBlank(message = "상품 ID는 null이 될 수 없습니다.")
        private Long productId;

        @NotBlank @Size(max = 10)
        private ProcessMean processMean;

        @NotBlank @Size(max = 20)
        private String largeArea;

        @NotBlank @Size(max = 20)
        private String detailArea;

        @NotBlank
        @Future
        private LocalDateTime reservationAT;

        @NotBlank @Size(max = 500)
        private String content;

        // TODO: 특정 확장자만 가능하도록 필터링 해야함
        @Builder.Default
        private List<MultipartFile> attachedFileList = new ArrayList<>();

        private Integer cost;

        public ReservationReq toReq(Long consulterId) {
            return ReservationReq.builder()
                    .consulterId(consulterId)
                    .consulterName(consulterName)
                    .consultantId(consultantId)
                    .consultantName(consultantName)
                    .productId(productId)
                    .processMean(processMean)
                    .largeArea(largeArea)
                    .detailArea(detailArea)
                    .reservationAT(reservationAT)
                    .content(content)
                    .attachedFileList(attachedFileList)
                    .cost(cost)
                    .build();
        }
    }


}
