package com.devtalk.consultation.consultationservice.consultation.application.validator;

import com.devtalk.consultation.consultationservice.consultation.application.port.out.client.ProductServiceClient;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.LinkItemQueryableRepository;
import com.devtalk.consultation.consultationservice.global.error.execption.DuplicationException;
import com.devtalk.consultation.consultationservice.global.error.execption.InvalidInputException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationReq.*;
import static com.devtalk.consultation.consultationservice.consultation.application.port.out.client.dto.ProductRes.*;
import static com.devtalk.consultation.consultationservice.global.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ConsultationValidator {

    private final ProductServiceClient productServiceClient;
    private final LinkItemQueryableRepository linkItemQueryableRepository;
    private final FileValidator fileValidator;

    // TODO: MaxUploadSizeExceededException 예외 처리해주기
    // 파일 확장자가 png, jpg, jpeg, pdf, ppt, xlsx. xls, doc, docx, txt 인지 검증
    public void validate(ReservationReq reservationReq) {
        validateAttachedFileList(reservationReq.getAttachedFileList());
        validateProduct(reservationReq);
    }

    // TODO: 파일 3개 이하 개수 체크 + 확장자 체크 + 파일 사이즈 체크
    private void validateAttachedFileList(List<MultipartFile> attachedFileList) {
        fileValidator.checkExceedMaxCount(attachedFileList.size());
        fileValidator.checkExceedMaxSize(attachedFileList);
        fileValidator.checkFileExtension(attachedFileList);
    }

    // TODO: reservationReq 의 내용과 ProductSearchRes 의 내용이 같은지 비교함
    private void validateProduct(ReservationReq reservationReq) {
        ProductSearchRes realProduct = productServiceClient.getProduct(reservationReq.getProductId());

        if (reservationReq.getConsultantId() != realProduct.getConsultantId() ||
                reservationReq.getReservationAT() != realProduct.getReservationAT() ||
                reservationReq.getProcessMean() != realProduct.getProcessMean() ||
                reservationReq.getCost() != realProduct.getCost() ||
                !realProduct.getReservationStatus().equals(ProductStatus.AVAILABLE.getStatus())) {

            throw new InvalidInputException(INVALID_RESERVATION_REQUEST);
        }

        if(linkItemQueryableRepository.existsByProductIdInReservedItem(reservationReq.getProductId()) == true) {
            throw new DuplicationException(DUPLICATED_RESERVATION);
        }
    }

    private enum ProductStatus {
        RESERVED("RESERVED"),
        CANCELED("CANCELED"),
        AVAILABLE("AVAILABLE");
        private String status;

        ProductStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }
}
