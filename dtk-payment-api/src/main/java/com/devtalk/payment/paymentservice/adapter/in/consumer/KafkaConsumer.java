package com.devtalk.payment.paymentservice.adapter.in.consumer;

import com.devtalk.payment.global.config.CustomLocalDateTimeDeserializer;
import com.devtalk.payment.paymentservice.adapter.in.consumer.dto.ConsumerInput;
import com.devtalk.payment.paymentservice.application.port.in.PaymentUseCase;
import com.devtalk.payment.paymentservice.application.port.in.RefundUseCase;
import com.devtalk.payment.paymentservice.application.port.out.repository.ConsultationRepo;
import com.devtalk.payment.paymentservice.application.port.out.repository.PaymentRepo;
import com.devtalk.payment.paymentservice.domain.consultation.Consultation;
import com.devtalk.payment.paymentservice.domain.payment.Payment;
import com.devtalk.payment.paymentservice.domain.payment.PaymentStatus;
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

import static com.devtalk.payment.paymentservice.adapter.in.consumer.dto.ConsumerInput.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    private final PaymentUseCase paymentUseCase;
    private final RefundUseCase refundUseCase;

    @KafkaListener(topics = "approved-consultation-topic")
    public void receiveConsultationInfo(String kafkaMessage) {
        log.info("Kafka Message: " + kafkaMessage);
        ConsultationInput consultation = null;
        try{
            // 카프카로 받은 메시지를 JSON 형태로 변환하기 위한 과정
            consultation = deserializeMapper().readValue(kafkaMessage, ConsultationInput.class);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        paymentUseCase.recieveAcceptedConsultation(consultation);
    }

    // TODO: 결제 취소건 topic 받기
    @KafkaListener(topics = "refund-consultation-topic")
    public void receiveCanceledInfo(String kafkaMessage) {
        log.info("kafka Message: " + kafkaMessage);
        ConsultationInput consultation = null;
        try{
            // 카프카로 받은 메시지를 JSON 형태로 변환하기 위한 과정
            consultation = deserializeMapper().readValue(kafkaMessage, ConsultationInput.class);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        refundUseCase.cancelPayment(consultation.getConsultationId(), consultation.getConsulterId());
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
