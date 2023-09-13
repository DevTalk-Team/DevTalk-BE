package com.devtalk.payment.paymentservice.unittest;

import com.devtalk.payment.paymentservice.application.ConsultationService;
import com.devtalk.payment.paymentservice.application.PaymentService;
import com.devtalk.payment.paymentservice.application.port.in.EmailUseCase;
import com.devtalk.payment.paymentservice.application.port.in.dto.PaymentRes;
import com.devtalk.payment.paymentservice.application.port.out.client.MemberServiceClient;
import com.devtalk.payment.paymentservice.application.port.out.client.dto.MemberRes;
import com.devtalk.payment.paymentservice.application.port.out.repository.ConsultationRepo;
import com.devtalk.payment.paymentservice.application.port.out.repository.PaymentRepo;
import com.devtalk.payment.paymentservice.domain.consultation.Consultation;
import com.devtalk.payment.paymentservice.domain.consultation.ProcessStatus;
import com.devtalk.payment.paymentservice.domain.payment.Payment;
import com.devtalk.payment.paymentservice.domain.payment.PaymentStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.devtalk.payment.paymentservice.application.port.in.dto.PaymentRes.*;
import static com.devtalk.payment.paymentservice.application.port.out.client.dto.MemberRes.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceUnitTest {

    @InjectMocks ConsultationService consultationService;
    @InjectMocks PaymentService paymentService;

    @Mock PaymentRepo paymentRepo;
    @Mock ConsultationRepo consultationRepo;
    @Mock EmailUseCase emailUseCase;
    @Mock MemberServiceClient memberServiceClient;

    @Test
    void 페인클라이언트(){
        MemberInfoRes consulter = memberServiceClient.getMemberInfo(1L);
        System.out.println(consulter.toString());
    }

    @Test
    void 이메일_전송(){
        Consultation consultation = getConsultationInfo();
        emailUseCase.sendPaymentSuccessEmail(consultation);
    }

    @Test
    void 토큰생성(){
        System.out.println("expire_at : " + (System.currentTimeMillis()/1000 + 1800));
        System.out.println(paymentService.getToken());
    }

    @Test
    @DisplayName("조회 성공")
    void 결제상세_조회성공(){
        // given
        Payment paymentInfo = getPaymentInfo();
        given(paymentRepo.findByConsultationId(anyLong())).willReturn(Optional.of(paymentInfo));

        // when
        PaymentSearchRes paymentInfoRes = paymentService.searchPaymentInfo(1L);

        // then
        assertThat(paymentInfoRes).isEqualTo(paymentInfo);
    }

    private Payment getPaymentInfo(){
        return Payment.builder()
                .id(1L)
                .paymentUid("imp_001")
                .cost(10000)
                .paidAt(LocalDateTime.now())
                .consultation(getConsultationInfo())
                .status(PaymentStatus.PAID)
                .build();
    }

    private Consultation getConsultationInfo(){
        return Consultation.builder()
                .id(1L)
                .merchantId(UUID.randomUUID().toString())
                .consulter("홍길동")
                .consulterEmail("zktl34@naver.com")
                .consultant("박주형")
                .consultationType("15분 전화상담")
                .consultationAt(LocalDateTime.now())
                .processStatus(ProcessStatus.WAITING_CONSULTATION)
                .build();
    }

}
