package com.devtalk.consultation.consultationservice.unittest;

import com.devtalk.consultation.consultationservice.consultation.application.ReserveService;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.ConsultationRepo;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.MemberQueryableRepo;
import com.devtalk.consultation.consultationservice.consultation.application.validator.ConsultationValidator;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.Consultation;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.ProcessMean;
import com.devtalk.consultation.consultationservice.consultation.domain.member.Consultant;
import com.devtalk.consultation.consultationservice.consultation.domain.member.Consulter;
import com.devtalk.consultation.consultationservice.consultation.domain.member.RoleType;
import com.devtalk.consultation.consultationservice.global.error.execption.FileException;
import com.devtalk.consultation.consultationservice.global.error.execption.NotFoundException;
import com.devtalk.consultation.consultationservice.global.util.FileUploadService;
import com.devtalk.consultation.consultationservice.global.vo.BaseFile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationReq.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ReserveServiceUnitTest {

    @InjectMocks ReserveService reserveService;

    @Mock ConsultationValidator consultationValidator;
    @Mock ConsultationRepo consultationRepo;
    @Mock
    FileUploadService fileUploadUtils;
    @Mock MemberQueryableRepo memberQueryableRepo;

    /** 테스트 케이스 (Validator 테스트는 분리하여 진행)
     * S. 상담 예약 성공
     * F. 상담 예약 실패
        * F1. 상담 예약 실패 - 상담 기록부가 등록되어 있지 않음
        * F2. 상담 예약 실패 - 상담자가 등록되어 있지 않음
        * F3. 상담 예약 실패 - 파일 업로드 실패(파일 시스템 에러)
     */

    @Test
    @DisplayName("S. 예약 성공")
    void 상담예약성공() {
        // given
        Consulter consulter = getConsulter();
        Consultation consultation = getConsultation(consulter);
        Consultant consultant = getConsultant();
        Long productId = 3L;

        MockMultipartFile attachedFile1 = new MockMultipartFile("file1", "file1.pdf", "pdf", "example".getBytes(StandardCharsets.UTF_8));
        MockMultipartFile attachedFile2 = new MockMultipartFile("file2", "file2.xslx", "xslx", "example".getBytes(StandardCharsets.UTF_8));
        List<MultipartFile> attachedFileList = List.of(attachedFile1, attachedFile2);
        ReservationReq reservationReq = getReservationReq(consulter.getId(), consultant.getId(), productId, attachedFileList);

        willDoNothing().given(consultationValidator).validate(reservationReq);
        given(consultationRepo.findByConsulterId(consulter.getId())).willReturn(Optional.of(consultation));
        given(memberQueryableRepo.findByConsultantId(consultant.getId())).willReturn(Optional.of(consultant));
        given(fileUploadUtils.uploadFileList(reservationReq.getAttachedFileList())).willReturn(convertToBaseFile(attachedFileList));
        given(consultationRepo.save(any())).willReturn(consultation);

        // when
       reserveService.reserve(reservationReq);

        // then
        then(consultationRepo).should(times(1)).save(any());
    }

    @Test
    @DisplayName("F1. 상담 예약 실패 - 상담 기록부가 등록되어 있지 않음")
    void 상담예약실패_상담기록부미등록() {
        // given
        Consulter consulter = getConsulter();
        Consultation consultation = getConsultation(consulter);
        Consultant consultant = getConsultant();
        Long productId = 3L;

        MockMultipartFile attachedFile1 = new MockMultipartFile("file1", "file1.pdf", "pdf", "example".getBytes(StandardCharsets.UTF_8));
        MockMultipartFile attachedFile2 = new MockMultipartFile("file2", "file2.xslx", "xslx", "example".getBytes(StandardCharsets.UTF_8));
        List<MultipartFile> attachedFileList = List.of(attachedFile1, attachedFile2);
        ReservationReq reservationReq = getReservationReq(consulter.getId(), consultant.getId(), productId, attachedFileList);

        willDoNothing().given(consultationValidator).validate(reservationReq);
        given(consultationRepo.findByConsulterId(consulter.getId())).willReturn(Optional.ofNullable(null));

        // when, then
        assertThrows(NotFoundException.class, () -> reserveService.reserve(reservationReq));
    }

    @Test
    @DisplayName("F2. 상담 예약 실패 - 상담자가 등록되어 있지 않음")
    void 상담예약실패_상담자미등록() {
        // given
        Consulter consulter = getConsulter();
        Consultation consultation = getConsultation(consulter);
        Consultant consultant = getConsultant();
        Long productId = 3L;

        MockMultipartFile attachedFile1 = new MockMultipartFile("file1", "file1.pdf", "pdf", "example".getBytes(StandardCharsets.UTF_8));
        MockMultipartFile attachedFile2 = new MockMultipartFile("file2", "file2.xslx", "xslx", "example".getBytes(StandardCharsets.UTF_8));
        List<MultipartFile> attachedFileList = List.of(attachedFile1, attachedFile2);
        ReservationReq reservationReq = getReservationReq(consulter.getId(), consultant.getId(), productId, attachedFileList);

        willDoNothing().given(consultationValidator).validate(reservationReq);
        given(consultationRepo.findByConsulterId(consulter.getId())).willReturn(Optional.of(consultation));
        given(memberQueryableRepo.findByConsultantId(consultant.getId())).willReturn(Optional.ofNullable(null));

        // when, then
        assertThrows(NotFoundException.class, () -> reserveService.reserve(reservationReq));
    }

    @Test
    @DisplayName("F3. 상담 예약 실패 - 파일 업로드 실패(파일 시스템 에러)")
    void 상담예약실패_파일업로드실패() {
        // given
        Consulter consulter = getConsulter();
        Consultation consultation = getConsultation(consulter);
        Consultant consultant = getConsultant();
        Long productId = 3L;

        MockMultipartFile attachedFile1 = new MockMultipartFile("file1", "file1.pdf", "pdf", "example".getBytes(StandardCharsets.UTF_8));
        MockMultipartFile attachedFile2 = new MockMultipartFile("file2", "file2.xslx", "xslx", "example".getBytes(StandardCharsets.UTF_8));
        List<MultipartFile> attachedFileList = List.of(attachedFile1, attachedFile2);
        ReservationReq reservationReq = getReservationReq(consulter.getId(), consultant.getId(), productId, attachedFileList);

        willDoNothing().given(consultationValidator).validate(reservationReq);
        given(consultationRepo.findByConsulterId(consulter.getId())).willReturn(Optional.of(consultation));
        given(memberQueryableRepo.findByConsultantId(consultant.getId())).willReturn(Optional.of(consultant));
        given(fileUploadUtils.uploadFileList(reservationReq.getAttachedFileList())).willThrow(FileException.class);

        // when, then
        assertThrows(FileException.class, () -> reserveService.reserve(reservationReq));
    }

    private ReservationReq getReservationReq(Long consulterId, Long consultantId, Long productId, List<MultipartFile> attachedFileList) {
        return ReservationReq.builder()
                .consulterId(consulterId)
                .consultantId(consultantId)
                .productId(productId)
                .processMean(ProcessMean.PHONE)
                .largeArea("커리어 상담")
                .detailArea("웹")
                .reservationAT(LocalDateTime.now().plusDays(1))
                .content("이직을 준비하고 있는데 어떤 것을 준비해야 할까요?")
                .attachedFileList(attachedFileList)
                .cost(10000)
                .build();
    }

    private Consulter getConsulter() {
        return Consulter.builder()
                .id(2L)
                .loginId("consulter@example.com")
                .name("내담자1")
                .role(RoleType.ROLE_CONSULTER)
                .build();
    }

    private Consultation getConsultation(Consulter consulter) {
        return Consultation.builder()
                .consulter(consulter)
                .reservedItemList(new ArrayList<>())
                .canceledItemList(new ArrayList<>())
                .build();
    }

    private Consultant getConsultant() {
        return Consultant.builder()
                .id(3L)
                .loginId("consultant@example.com")
                .name("상담자1")
                .role(RoleType.ROLE_CONSULTANT)
                .occupation("백엔드 개발자")
                .career(5)
                .build();
    }



    private List<BaseFile> convertToBaseFile(List<MultipartFile> attachedFileList) {
        List<BaseFile> fileList = new ArrayList<>();

        attachedFileList.stream().forEach(attachedFile -> {
            fileList.add(
                    BaseFile.builder()
                    .storedName(attachedFile.getName() + "random")
                    .originName(attachedFile.getName())
                    .fileUrl("/consultations")
                    .build());
        });
        return fileList;
    }
}