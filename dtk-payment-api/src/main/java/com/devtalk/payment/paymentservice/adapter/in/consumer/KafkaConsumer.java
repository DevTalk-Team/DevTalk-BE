package com.devtalk.payment.paymentservice.adapter.in.consumer;

import com.devtalk.payment.global.config.CustomLocalDateTimeDeserializer;
import com.devtalk.payment.global.config.CustomLocalDateTimeSerializer;
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
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
//        Map<Object, Object> map = new HashMap<>();

        Consultation consultation = null;
        try{
            // 카프카로 받은 메시지를 JSON 형태로 변환하기 위한 과정
            consultation = deserializeMapper().readValue(kafkaMessage, Consultation.class);
//            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
        } catch (JsonProcessingException ex){
            ex.printStackTrace();
        }
        dataSynchronization(consultation);
    }

    private void dataSynchronization(Consultation consultation) {
        // 카프카로 받은 예약 정보를 저장
        consultationRepo.save(consultation);
//        consultationRepo.save((Consultation) map);
        // Payment에 임시 결제 정보 생성
//        Consultation consultation = consultationUseCase.searchConsultationInfo(((Consultation) map).getId());
        paymentRepo.save(Payment.builder()
                .consultation(consultation)
                .paymentUid(null)
                .cost(consultation.getCost())
                .paidAt(null)
                .status(PaymentStatus.READY)
                .build());
    }

    public ObjectMapper deserializeMapper(){
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(LocalDateTime.class, new CustomLocalDateTimeDeserializer());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(simpleModule);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper;
    }
}
