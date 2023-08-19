package com.devtalk.consultation.consultationservice.integrationtest;

import com.devtalk.consultation.consultationservice.consultation.adapter.in.web.ConsultationApiController;
import com.devtalk.consultation.consultationservice.consultation.application.ReserveService;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.client.ProductServiceClient;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.client.dto.ProductRes;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.ConsultationRepo;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.ConsultationQueryableRepo;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.MemberQueryableRepo;
import com.devtalk.consultation.consultationservice.consultation.application.validator.ConsultationValidator;
import com.devtalk.consultation.consultationservice.consultation.application.validator.FileValidator;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.Consultation;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.ProcessStatus;
import com.devtalk.consultation.consultationservice.consultation.domain.member.Consultant;
import com.devtalk.consultation.consultationservice.consultation.domain.member.Consulter;
import com.devtalk.consultation.consultationservice.global.util.FileUploadService;
import com.devtalk.consultation.consultationservice.integrationtest.setup.ConsultationSetUp;
import com.devtalk.consultation.consultationservice.integrationtest.setup.MemberSetUp;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static com.devtalk.consultation.consultationservice.consultation.adapter.in.web.dto.ConsultationInput.*;
import static com.devtalk.consultation.consultationservice.consultation.domain.consultation.ProcessMean.*;
import static com.devtalk.consultation.consultationservice.consultation.domain.member.RoleType.*;
import static org.mockito.BDDMockito.*;

/**
    * 테스트 케이스
    * S. 상담 예약 성공
    * F. 상담 예약 실패
    * F1. 상담 예약 실패 - 상담 기록부가 등록되어 있지 않음
    * F2. 상담 예약 실패 - 상담자가 등록되어 있지 않음
    * F3. 상담 예약 실패 - 파일 업로드 실패(파일 시스템 에러)
 */

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@ExtendWith(MockitoExtension.class)
class ConsultationApiControllerTest {

    final Integer fileListMaxSize = 10485760;
    final Integer fileListMaxCount = 3;

    private ConsultationApiController consultationApiController;

    // ReserveService 멤버
    @Autowired private FileUploadService fileUploadService;
    @Autowired private ConsultationRepo consultationRepo;

    // ConsultationValidator 멤버
    private ProductServiceClient productServiceClient;
    @Autowired private MemberQueryableRepo memberQueryableRepo;
    @Autowired private ConsultationQueryableRepo consultationQueryableRepo;

    @Autowired private MemberSetUp memberSetup;
    @Autowired private ConsultationSetUp consultationSetUp;

    @BeforeAll
    void setUp() {
        productServiceClient = Mockito.mock(ProductServiceClient.class);
        ConsultationValidator consultationValidator = new ConsultationValidator(productServiceClient, consultationQueryableRepo, memberQueryableRepo, new FileValidator(fileListMaxSize, fileListMaxCount));
        consultationApiController = new ConsultationApiController(new ReserveService(consultationValidator, fileUploadService, consultationRepo));
    }

    @Test
    @DisplayName("S. 상담 예약 성공")
    void 예약요청성공() throws Exception {
        //given
        Consultant consultant = memberSetup.saveConsultant(1L, "exLoginId1", "홍길동", ROLE_CONSULTANT, "백엔드 개발자", 4);
        Consulter consulter = memberSetup.saveConsulter(2L, "exLoginId2", "김철수", ROLE_CONSULTER);
        MockMultipartFile attachedFile1 = new MockMultipartFile("file1", "file1.pdf", "pdf", "example".getBytes(StandardCharsets.UTF_8));
        MockMultipartFile attachedFile2 = new MockMultipartFile("file2", "file2.xlsx", "xslx", "example".getBytes(StandardCharsets.UTF_8));
        List<MultipartFile> attachedFileList = List.of(attachedFile1, attachedFile2);

        ReservationInput reservationInput = ReservationInput.builder()
                .consultantId(consultant.getId())
                .consultantName(consultant.getName())
                .consulterName(consulter.getName())
                .productId(1L)
                .processMean(PHONE)
                .largeArea("커리어 상담")
                .detailArea("웹 개발")
                .reservationAT(LocalDateTime.now().plusDays(1))
                .content("이직을 하려고 하는데 어떨까요?")
                .attachedFileList(attachedFileList)
                .cost(10000)
                .build();

        given(productServiceClient.getProduct(reservationInput.getProductId())).willReturn(
                ProductRes.ProductSearchRes.builder()
                        .consultantId(reservationInput.getConsultantId()).
                        reservationAT(reservationInput.getReservationAT())
                        .processMean(reservationInput.getProcessMean())
                        .cost(reservationInput.getCost())
                        .reservationStatus("AVAILABLE")
                        .build());

        //when
        consultationApiController.reserveConsultation(reservationInput, consulter.getId());

        //then
        Consultation reservedItemByProductId = consultationSetUp.findConsultationByProductId(reservationInput.getProductId(), ProcessStatus.ACCEPT_WAIT);
        Assertions.assertEquals(reservationInput.getConsultantId(), reservedItemByProductId.getConsultantId());
    }

}