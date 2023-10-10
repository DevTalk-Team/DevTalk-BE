package com.devtalk.consultation.consultationservice.consultation.application;

import com.devtalk.consultation.consultationservice.consultation.application.port.in.ModifyConsultationUseCase;
import com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationReq;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.AttachedFileRepo;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.ConsultationQueryableRepo;
import com.devtalk.consultation.consultationservice.consultation.application.validator.FileValidator;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.AttachedFile;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.Consultation;
import com.devtalk.consultation.consultationservice.global.error.ErrorCode;
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
public class ModifyConsultationService implements ModifyConsultationUseCase {

    private final FileValidator fileValidator;
    private final FileUploadService fileUploadService;
    private final ConsultationQueryableRepo consultationQueryableRepo;
    private final AttachedFileRepo attachedFileRepo;


    @Transactional
    @Override
    public Long modifyConsultation(ConsultationModificationReq modificationReq) {
        //List<MultipartFile> newMultipartFileList = modificationReq.getAttachedFileList();
        //fileValidator.checkFileCountAndSizeAndExtension(newMultipartFileList);

        Consultation findConsultation = consultationQueryableRepo.findByIdWithConsulterId(modificationReq.getConsultationId(), modificationReq.getConsulterId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_CONSULTATION));
        //List<AttachedFile> newAttachedFileList = uploadAttachedFileList(findConsultation,newMultipartFileList);


        //findConsultation.modifyDetails(modificationReq.getContent(), newAttachedFileList);

        return findConsultation.getId();
    }

    private void uploadAttachedFileList(Consultation newConsultation, List<MultipartFile> attachedFileList) {
        List<BaseFile> baseFileList = fileUploadService.uploadConsultationFileList(attachedFileList);

        baseFileList.forEach(baseFile ->
                attachedFileRepo.save(
                        AttachedFile.createAttachedFile(
                                newConsultation,
                                baseFile.getFileUrl(),
                                baseFile.getOriginFileName(),
                                baseFile.getOriginFileName()
                        ))
        );
    }
}
