package com.devtalk.consultation.consultationservice.unittest.validator;

import com.devtalk.consultation.consultationservice.consultation.application.port.out.client.ProductServiceClient;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.ConsultationQueryableRepo;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.MemberQueryableRepo;
import com.devtalk.consultation.consultationservice.consultation.application.validator.ConsultationValidator;
import com.devtalk.consultation.consultationservice.consultation.application.validator.FileValidator;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.ProcessMean;
import com.devtalk.consultation.consultationservice.consultation.domain.member.Consultant;
import com.devtalk.consultation.consultationservice.consultation.domain.member.Consulter;
import com.devtalk.consultation.consultationservice.consultation.domain.member.RoleType;
import com.devtalk.consultation.consultationservice.global.error.execption.DuplicationException;
import com.devtalk.consultation.consultationservice.global.error.execption.FileException;
import com.devtalk.consultation.consultationservice.global.error.execption.InvalidInputException;
import com.devtalk.consultation.consultationservice.global.error.execption.NotFoundException;
import com.devtalk.consultation.consultationservice.global.vo.BaseFile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationReq.*;
import static com.devtalk.consultation.consultationservice.consultation.application.port.out.client.dto.ProductRes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

/** 테스트 케이스
 * S. 상담 예약 검증 통과
 * F. 상담 예약 검증 실패
 * F1. 상담 예약 실패 - 첨부파일의 최대 허용 개수 초과
 * F2. 상담 예약 실패 - 첨부파일 리스트의 총 용량이 최대 허용 용량 초과
 * F3. 상담 예약 실패 - 금지된 확장자를 가진 파일이 첨부파일에 포함됨
 * F4. 상담 예약 실패 - 존재하지 않는 내담자
 * F5. 상담 예약 실패 - 존재하지 않는 상담사
 * F6. 상담 예약 실패 - 외부 상품 서비스의 응답으로 들어온 전문가의 id와 요청으로 들어온 전문가 id가 불일치
 * F7. 상담 예약 실패 - 외부 상품 서비스에서 응답으로 들어온 상품 상태가 예약 불가 상태
 * F8. 상담 예약 실패 - 이미 예약된 상품(상품 서비스에서는 이용가능하다는 응답받고 그 사이에 예약되버림)
 */

@ExtendWith(MockitoExtension.class)
public class ConsultationValidatorUnitTest {
    final Integer fileListMaxSize = 10485760;
    final Integer fileListMaxCount = 3;

    @InjectMocks ConsultationValidator consultationValidator;
    @Mock ProductServiceClient productServiceClient;

    @Mock MemberQueryableRepo memberQueryableRepo;
    @Mock
    ConsultationQueryableRepo consultationQueryableRepo;

    @Spy FileValidator fileValidator = new FileValidator(fileListMaxSize, fileListMaxCount);

    @Test
    @DisplayName("S. 상담 예약 검증 통과")
    void 상담예약실패_검증통과() {
        // given
        Consulter consulter = getConsulter();
        Consultant consultant = getConsultant();
        Long productId = 3L;

        List<MultipartFile> attachedFileList = new ArrayList<>();
        for (int i = 0; i < fileListMaxCount-1; i++) {
            attachedFileList.add(new MockMultipartFile("file1", "file1.pdf", "pdf", new byte[fileListMaxSize/(fileListMaxCount-1)-1]));
        }

        ReservationReq reservationReq = getReservationReq(consulter.getId(), consultant.getId(), productId, attachedFileList);

        given(memberQueryableRepo.existsByConsulterId(reservationReq.getConsulterId())).willReturn(true);
        given(memberQueryableRepo.existsByConsultantId(reservationReq.getConsultantId())).willReturn(true);
        given(productServiceClient.getProduct(productId)).willReturn(
                ProductSearchRes.builder()
                        .consultantId(reservationReq.getConsultantId()).
                        reservationAT(reservationReq.getReservationAT())
                        .processMean(reservationReq.getProcessMean())
                        .cost(reservationReq.getCost())
                        .reservationStatus("AVAILABLE")
                        .build());
        given(consultationQueryableRepo.existsByProductIdInReservedItem(reservationReq.getProductId())).willReturn(false);

        // when, then
        assertDoesNotThrow(() -> consultationValidator.validate(reservationReq));
    }

    @Test
    @DisplayName("F1. 상담 예약 실패 - 첨부파일의 최대 허용 개수 초과")
    void 상담예약실패_첨부파일최대허용개수초과() {
        // given
        Consulter consulter = getConsulter();
        Consultant consultant = getConsultant();
        Long productId = 3L;

        List<MultipartFile> attachedFileList = new ArrayList<>();
        for (int i = 0; i < fileListMaxCount+1; i++) {
            attachedFileList.add(new MockMultipartFile("file1", "file1.pdf", "pdf", new byte[fileListMaxSize/(fileListMaxCount-1)-1]));
        }

        ReservationReq reservationReq = getReservationReq(consulter.getId(), consultant.getId(), productId, attachedFileList);

        // when, then
        assertThrows(FileException.class, () -> consultationValidator.validate(reservationReq));
    }

    @Test
    @DisplayName("F2. 상담 예약 실패 - 첨부파일 리스트의 총 용량이 최대 허용 용량 초과")
    void 상담예약실패_첨부파일최대허용용량초과() {
        // given
        Consulter consulter = getConsulter();
        Consultant consultant = getConsultant();
        Long productId = 3L;

        List<MultipartFile> attachedFileList = new ArrayList<>();
        for (int i = 0; i < fileListMaxCount-1; i++) {
            attachedFileList.add(new MockMultipartFile("file1", "file1.pdf", "pdf", new byte[fileListMaxSize/(fileListMaxCount-1)+1]));
        }

        ReservationReq reservationReq = getReservationReq(consulter.getId(), consultant.getId(), productId, attachedFileList);

        // when, then
        assertThrows(FileException.class, () -> consultationValidator.validate(reservationReq));
    }

    @Test
    @DisplayName("F3. 상담 예약 실패 - 금지된 확장자를 가진 파일이 첨부파일에 포함됨")
    void 상담예약실패_금지된확장자포함() {
        // given
        Consulter consulter = getConsulter();
        Consultant consultant = getConsultant();
        Long productId = 3L;

        List<MultipartFile> attachedFileList = new ArrayList<>();
        for (int i = 0; i < fileListMaxCount-1; i++) {
            attachedFileList.add(new MockMultipartFile("file1", "file1.sql", "sql", new byte[fileListMaxSize/(fileListMaxCount-1)-1]));
        }

        ReservationReq reservationReq = getReservationReq(consulter.getId(), consultant.getId(), productId, attachedFileList);

        // when, then
        assertThrows(FileException.class, () -> consultationValidator.validate(reservationReq));
    }

    @Test
    @DisplayName("F4. 상담 예약 실패 - 존재하지 않는 내담자")
    void 상담예약실패_존재하지않는내담자() {
        // given
        Consulter consulter = getConsulter();
        Consultant consultant = getConsultant();
        Long productId = 3L;

        List<MultipartFile> attachedFileList = new ArrayList<>();
        for (int i = 0; i < fileListMaxCount-1; i++) {
            attachedFileList.add(new MockMultipartFile("file1", "file1.pdf", "pdf", new byte[fileListMaxSize/(fileListMaxCount-1)-1]));
        }

        ReservationReq reservationReq = getReservationReq(consulter.getId(), consultant.getId(), productId, attachedFileList);

        given(memberQueryableRepo.existsByConsulterId(reservationReq.getConsulterId())).willReturn(false);

        // when, then
        assertThrows(NotFoundException.class, () -> consultationValidator.validate(reservationReq));
    }

    @Test
    @DisplayName("F5. 상담 예약 실패 - 존재하지 않는 상담사")
    void 상담예약실패_존재하지않는상담사() {
        // given
        Consulter consulter = getConsulter();
        Consultant consultant = getConsultant();
        Long productId = 3L;

        List<MultipartFile> attachedFileList = new ArrayList<>();
        for (int i = 0; i < fileListMaxCount-1; i++) {
            attachedFileList.add(new MockMultipartFile("file1", "file1.pdf", "pdf", new byte[fileListMaxSize/(fileListMaxCount-1)-1]));
        }

        ReservationReq reservationReq = getReservationReq(consulter.getId(), consultant.getId(), productId, attachedFileList);

        given(memberQueryableRepo.existsByConsulterId(reservationReq.getConsulterId())).willReturn(true);
        given(memberQueryableRepo.existsByConsultantId(reservationReq.getConsultantId())).willReturn(false);

        // when, then
        assertThrows(NotFoundException.class, () -> consultationValidator.validate(reservationReq));
    }

    @Test
    @DisplayName("F6. 상담 예약 실패 - 외부 상품 서비스의 응답으로 들어온 전문가의 id와 요청으로 들어온 전문가 id가 불일치")
    void 상담예약실패_상품정보불일치() {
        // given
        Consulter consulter = getConsulter();
        Consultant consultant = getConsultant();
        Long productId = 3L;

        List<MultipartFile> attachedFileList = new ArrayList<>();
        for (int i = 0; i < fileListMaxCount-1; i++) {
            attachedFileList.add(new MockMultipartFile("file1", "file1.pdf", "pdf", new byte[fileListMaxSize/(fileListMaxCount-1)-1]));
        }

        ReservationReq reservationReq = getReservationReq(consulter.getId(), consultant.getId(), productId, attachedFileList);

        given(memberQueryableRepo.existsByConsulterId(reservationReq.getConsulterId())).willReturn(true);
        given(memberQueryableRepo.existsByConsultantId(reservationReq.getConsultantId())).willReturn(true);
        given(productServiceClient.getProduct(productId)).willReturn(
                ProductSearchRes.builder()
                        .consultantId(consultant.getId() + 1L)
                        .reservationAT(LocalDateTime.now().plusDays(1))
                        .cost(30000)
                        .build());

        // when, then
        assertThrows(InvalidInputException.class, () -> consultationValidator.validate(reservationReq));
    }

    @Test
    @DisplayName("F7. 상담 예약 실패 - 외부 상품 서비스에서 응답으로 들어온 상품 상태가 예약 불가 상태")
    void 상담예약실패_상품예약불가상태() {
        // given
        Consulter consulter = getConsulter();
        Consultant consultant = getConsultant();
        Long productId = 3L;

        List<MultipartFile> attachedFileList = new ArrayList<>();
        for (int i = 0; i < fileListMaxCount-1; i++) {
            attachedFileList.add(new MockMultipartFile("file1", "file1.pdf", "pdf", new byte[fileListMaxSize/(fileListMaxCount-1)-1]));
        }

        ReservationReq reservationReq = getReservationReq(consulter.getId(), consultant.getId(), productId, attachedFileList);

        given(memberQueryableRepo.existsByConsulterId(reservationReq.getConsulterId())).willReturn(true);
        given(memberQueryableRepo.existsByConsultantId(reservationReq.getConsultantId())).willReturn(true);
        given(productServiceClient.getProduct(productId)).willReturn(
                ProductSearchRes.builder()
                        .consultantId(consultant.getId())
                        .reservationAT(LocalDateTime.now().plusDays(1))
                        .cost(30000)
                        .reservationStatus("RESERVED")
                        .build());

        // when, then
        assertThrows(InvalidInputException.class, () -> consultationValidator.validate(reservationReq));
    }

    @Test
    @DisplayName("F8. 상담 예약 실패 - 이미 예약된 상품(상품 서비스에서는 이용가능하다는 응답받고 그 사이에 예약되버림)")
    void 상담예약실패_이미예약된상품() {
        // given
        Consulter consulter = getConsulter();
        Consultant consultant = getConsultant();
        Long productId = 3L;

        List<MultipartFile> attachedFileList = new ArrayList<>();
        for (int i = 0; i < fileListMaxCount-1; i++) {
            attachedFileList.add(new MockMultipartFile("file1", "file1.pdf", "pdf", new byte[fileListMaxSize/(fileListMaxCount-1)-1]));
        }

        ReservationReq reservationReq = getReservationReq(consulter.getId(), consultant.getId(), productId, attachedFileList);

        given(memberQueryableRepo.existsByConsulterId(reservationReq.getConsulterId())).willReturn(true);
        given(memberQueryableRepo.existsByConsultantId(reservationReq.getConsultantId())).willReturn(true);
        given(productServiceClient.getProduct(productId)).willReturn(
                ProductSearchRes.builder()
                        .consultantId(reservationReq.getConsultantId()).
                        reservationAT(reservationReq.getReservationAT())
                        .processMean(reservationReq.getProcessMean())
                        .cost(reservationReq.getCost())
                        .reservationStatus("AVAILABLE")
                        .build());
        given(consultationQueryableRepo.existsByProductIdInReservedItem(reservationReq.getProductId())).willReturn(true);

        // when, then
        assertThrows(DuplicationException.class, () -> consultationValidator.validate(reservationReq));
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



    public List<BaseFile> convertToBaseFile(List<MultipartFile> attachedFileList) {
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
