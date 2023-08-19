package com.devtalk.payment.paymentservice.unittest;

import com.devtalk.payment.paymentservice.application.port.out.repository.ConsultationRepo;
import com.devtalk.payment.paymentservice.domain.consultation.Consultation;
import com.devtalk.payment.paymentservice.domain.consultation.ProcessStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class ConsultationUnitTest {
    @Autowired
    ConsultationRepo consultationRepo;

    @Test
    public void JPA_Auditing_Test(){
        Consultation consultationInfo = Consultation.builder()
                .consultant("홍길동")
                .consultationAt(LocalDateTime.now())
                .consultationType("15분 전화상담")
                .consulterEmail("test@naver.com")
                .consulter("이순신")
                .cost(10000)
                .id(1L)
                .processStatus(ProcessStatus.APPROVED)
                .build();

        consultationRepo.save(consultationInfo);
    }
}
