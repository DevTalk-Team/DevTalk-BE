package com.devtalk.consultation.consultationservice.consultation.application;

import com.devtalk.consultation.consultationservice.consultation.application.port.in.*;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.ConsultationRepo;
import com.devtalk.consultation.consultationservice.consultation.application.validator.ConsultationValidator;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.AttachedFile;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.Consultation;
import com.devtalk.consultation.consultationservice.global.util.FileUploadService;
import com.devtalk.consultation.consultationservice.global.vo.BaseFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationReq.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReserveService implements ReserveUseCase {
    private final ConsultationValidator consultationValidator;
    private final FileUploadService fileUploadService;
    private final ConsultationRepo consultationRepo;

    @Transactional
    public void reserve(ReservationReq reservationReq) {
        consultationValidator.validate(reservationReq); //파일 정책 체크 + 회원 존재여부 체크 + 상품 매칭 가능여부 체크 + 매칭 중복 체크

        List<AttachedFile> attachedFileList = uploadAttachedFileList(reservationReq.getAttachedFileList());
        Consultation newConsultation = reservationReq.toEntity(attachedFileList);

        consultationRepo.save(newConsultation);
    }

    private List<AttachedFile> uploadAttachedFileList(List<MultipartFile> attachedFileList) {
        List<BaseFile> baseFileList = fileUploadService.uploadFileList(attachedFileList);
        List<AttachedFile> uploadedAttachedFileList = new ArrayList<>();

        baseFileList.stream().forEach(baseFile -> {
            uploadedAttachedFileList.add(AttachedFile.builder()
                    .fileUrl(baseFile.getFileUrl())
                    .originName(baseFile.getOriginName())
                    .storedName(baseFile.getStoredName())
                    .build());
        });

        return uploadedAttachedFileList;
    }

}
