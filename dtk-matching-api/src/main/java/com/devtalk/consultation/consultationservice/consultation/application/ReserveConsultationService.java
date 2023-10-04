package com.devtalk.consultation.consultationservice.consultation.application;

import com.devtalk.consultation.consultationservice.consultation.adapter.out.producer.PaymentKafkaProducer;
import com.devtalk.consultation.consultationservice.consultation.adapter.out.producer.ProductKafkaProducer;
import com.devtalk.consultation.consultationservice.consultation.application.port.in.*;
import com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationReq;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.client.MemberServiceClient;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.client.dto.MemberRes;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.AttachedFileRepo;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.ConsultationRepo;
//import com.devtalk.consultation.consultationservice.consultation.application.validator.ConsultationReservationValidator;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.MemberQueryableRepo;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.MemberRepo;
import com.devtalk.consultation.consultationservice.consultation.application.validator.ConsultationReservationValidator;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.AttachedFile;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.Consultation;
import com.devtalk.consultation.consultationservice.consultation.domain.member.Consultant;
import com.devtalk.consultation.consultationservice.consultation.domain.member.Consulter;
import com.devtalk.consultation.consultationservice.consultation.domain.member.MemberType;
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
import static com.devtalk.consultation.consultationservice.global.error.ErrorCode.NOT_FOUND_CONSULTER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReserveConsultationService implements ReserveConsultationUseCase {
    private final ConsultationReservationValidator consultationValidator;
    private final FileUploadService fileUploadService;
    private final ConsultationRepo consultationRepo;
    private final MemberQueryableRepo memberQueryableRepo;
    private final MemberServiceClient memberServiceClient;

    private final AttachedFileRepo attachedFileRepo;
    private final MemberRepo memberRepo;

    @Transactional
    public void reserve(ReservationReq reservationReq, List<MultipartFile> attachedFiles) {
        //consultationValidator.validateReservation(reservationReq); //파일 정책 체크 + 회원 존재여부 체크 + 상품 매칭 가능여부 체크 + 매칭 중복 체크
        if (!memberQueryableRepo.existsByConsultantId(reservationReq.getConsultantId())){
            MemberRes.ConsultantRes consultantInfo = memberServiceClient.getConsultantInfo(reservationReq.getConsultantId());
            if(consultantInfo.getMemberType() == MemberType.CONSULTANT) {
                Consultant newConsultant = Consultant.createConsultant(consultantInfo.getConsultantId(), consultantInfo.getName());
                memberRepo.save(newConsultant);
            }

        }
        if (!memberQueryableRepo.existsByConsulterId(reservationReq.getConsulterId())){
            MemberRes.ConsulterRes consulterInfo = memberServiceClient.getConsulterInfo(reservationReq.getConsultantId());
            if(consulterInfo.getMemberType() == MemberType.CONSULTER) {
                Consulter newConsulter = Consulter.createConsulter(consulterInfo.getConsulterId(), consulterInfo.getName());
                memberRepo.save(newConsulter);
            }
        }
        Consultation newConsultation = reservationReq.toEntity();
        newConsultation = consultationRepo.save(newConsultation);
        if(reservationReq.getAttachedFileList() != null){
            uploadAttachedFileList(newConsultation, attachedFiles);
        }
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
