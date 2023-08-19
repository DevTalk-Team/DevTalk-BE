package com.devtalk.consultation.consultationservice.consultation.application;

import com.devtalk.consultation.consultationservice.consultation.application.port.in.*;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.*;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.ConsultationRepo;
import com.devtalk.consultation.consultationservice.consultation.application.validator.ConsultationValidator;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.AttachedFile;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.Consultation;
import com.devtalk.consultation.consultationservice.consultation.domain.member.Consultant;
import com.devtalk.consultation.consultationservice.global.error.execption.NotFoundException;
import com.devtalk.consultation.consultationservice.global.util.FileUploadService;
import com.devtalk.consultation.consultationservice.global.vo.BaseFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationReq.*;
import static com.devtalk.consultation.consultationservice.global.error.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReserveService implements ReserveUseCase {
    private final ConsultationValidator consultationValidator;
    private final FileUploadService fileUploadService;
    private final ConsultationRepo consultationRepo;
    private final MemberQueryableRepo memberQueryableRepo;

    @Transactional
    public void reserve(ReservationReq reservationReq) {
        consultationValidator.validate(reservationReq);
        Consultation consultation = searchConsultation(reservationReq.getConsulterId());

        Consultant consultant = searchConsultant(reservationReq.getConsultantId());

        List<AttachedFile> attachedFileList = uploadAttachedFileList(reservationReq.getAttachedFileList());

        consultation.reserve(consultant, reservationReq.getProductId(),
                reservationReq.getProcessMean(), reservationReq.getLargeArea(), reservationReq.getDetailArea(),
                reservationReq.getReservationAT(), reservationReq.getContent(), attachedFileList, reservationReq.getCost());

        consultationRepo.save(consultation);
    }

    private Consultation searchConsultation(Long consulterId) {
        return consultationRepo.findByConsulterId(consulterId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_CONSULTATION));
    }

    private Consultant searchConsultant(Long consultantId) {
        return memberQueryableRepo.findByConsultantId(consultantId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_CONSULTANT));
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
