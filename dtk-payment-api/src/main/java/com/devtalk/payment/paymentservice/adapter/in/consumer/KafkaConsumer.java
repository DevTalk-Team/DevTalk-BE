package com.devtalk.payment.paymentservice.adapter.in.consumer;

import com.devtalk.payment.paymentservice.application.port.in.ConsultationUseCase;
import com.devtalk.payment.paymentservice.application.port.in.PaymentUseCase;
import com.devtalk.payment.paymentservice.application.port.out.repository.ConsultationRepo;
import com.devtalk.payment.paymentservice.application.port.out.repository.PaymentRepo;
import com.devtalk.payment.paymentservice.domain.consultation.Consultation;
import com.devtalk.payment.paymentservice.domain.payment.Payment;
import com.devtalk.payment.paymentservice.domain.payment.PaymentStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    private final ConsultationUseCase consultationUseCase;
    private final PaymentUseCase paymentUseCase;
    private final ConsultationRepo consultationRepo;
    private final PaymentRepo paymentRepo;

    @KafkaListener(topics = "consultation-topic")
    public void receiveConsultationInfo(String kafkaMessage) {
        log.info("Kafka Message: " + kafkaMessage);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try{
            // 카프카로 받은 메시지를 JSON 형태로 변환하기 위한 과정
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
        } catch (JsonProcessingException ex){
            ex.printStackTrace();
        }
        // 카프카로 받은 예약 정보를 저장
        consultationRepo.save((Consultation) map);
        // Payment에 임시 결제 정보 생성
        Consultation consultation = consultationUseCase.searchConsultationInfo(((Consultation) map).getId());
        paymentRepo.save(Payment.builder()
                .consultation(consultation)
                .paymentUid(null)
                .cost(consultation.getCost())
                .paidAt(null)
                .status(PaymentStatus.READY)
                .build());
    }
}
