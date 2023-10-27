package com.devtalk.consultation.consultationservice.consultation.adapter.out.consumer;

import com.devtalk.consultation.consultationservice.consultation.adapter.in.web.dto.PaymentInput;
import com.devtalk.consultation.consultationservice.consultation.application.port.in.PaymentUseCase;
import com.devtalk.consultation.consultationservice.global.config.CustomLocalDateTimeDeserializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentKafkaConsumer {
    private final PaymentUseCase paymentUseCase;

    @KafkaListener(topics = "payment-status")
    public void receiveConsultationInfo(String kafkaMessage) {
        log.info("Kafka Message: " + kafkaMessage);
        PaymentInput paymentInput = null;
        try{
            // 카프카로 받은 메시지를 JSON 형태로 변환하기 위한 과정
            paymentInput = deserializeMapper().readValue(kafkaMessage, PaymentInput.class);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        paymentUseCase.paidConsultation(paymentInput);
    }

    public ObjectMapper deserializeMapper(){
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(LocalDateTime.class, new CustomLocalDateTimeDeserializer());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(simpleModule);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper;
    }
}