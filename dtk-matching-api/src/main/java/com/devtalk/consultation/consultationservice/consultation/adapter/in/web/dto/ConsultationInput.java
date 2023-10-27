package com.devtalk.consultation.consultationservice.consultation.adapter.in.web.dto;

import com.devtalk.consultation.consultationservice.consultation.domain.consultation.ProductProceedType;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationReq.*;

public class ConsultationInput {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ReservationInput {

        @NotNull(message = "상담자 ID는 null이 될 수 없습니다.")
        private Long consultantId;

        @NotBlank(message = "상담자 이름은 null이 될 수 없습니다.")
        private String consultantName;

        @NotBlank(message = "내담자 이름은 null이 될 수 없습니다.")
        private String consulterName;

        @NotNull(message = "상품 ID는 null이 될 수 없습니다.")
        private Long productId;

        @NotBlank
        private ProductProceedType productProceedType;


        @NotBlank @Size(max = 20)
        private String region;

        @Future
        private LocalDate reservationDate;
        private LocalTime reservationTime;


        @NotBlank @Size(max = 500)
        private String content;

//        // TODO: 특정 확장자만 가능하도록 필터링 해야함
//        @Builder.Default
//        private List<MultipartFile> attachedFileList = new ArrayList<>();

        private Integer cost;

        public ReservationReq toReq(Long consulterId) {
            return ReservationReq.builder()
                    .consulterId(consulterId)
                    .consulterName(consulterName)
                    .consultantId(consultantId)
                    .consultantName(consultantName)
                    .productId(productId)
                    .productProceedType(productProceedType)
                    .region(region)
                    .reservationDate(reservationDate)
                    .reservationTime(reservationTime)
                    .content(content)
                    .cost(cost)
                    .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class CancellationOfConsulterInput {

        @NotBlank @Size(max = 100)
        private String reason;

        public CancellationOfConsulterReq toReq(Long consulterId, Long consultationId) {
            return new CancellationOfConsulterReq(consulterId, consultationId, reason);
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class CancellationOfConsultantInput {

        @NotBlank @Size(max = 100)
        private String reason;

        public CancellationOfConsultantReq toReq(Long consultantId, Long consultationId) {
            return new CancellationOfConsultantReq(consultantId, consultationId, reason);
        }
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ConsultationModificationInput {

        @NotBlank
        @Size(max = 500)
        private String content;

        public ConsultationModificationReq toReq(Long consulterId, Long consultationId) {
            return new ConsultationModificationReq(consulterId, consultationId, content);
            // removed attachedFileList from here as well.
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ReviewInput {

        @Max(5) @Min(0)
        private Integer score;

        @NotBlank @Size(max = 500)
        private String content;

        private MultipartFile photo;

        public ReviewReq toReq(Long consulterId, Long consultationId) {
            return new ReviewReq(consulterId, consultationId, score, content, photo);
        }
    }


}
