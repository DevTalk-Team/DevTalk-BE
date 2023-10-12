package com.devtalk.payment.paymentservice.adapter.in.consumer;

import com.devtalk.payment.global.config.CustomLocalDateTimeDeserializer;
import com.devtalk.payment.paymentservice.adapter.in.consumer.dto.ConsumerInput;
import com.devtalk.payment.paymentservice.application.port.out.repository.ConsultationRepo;
import com.devtalk.payment.paymentservice.application.port.out.repository.PaymentRepo;
import com.devtalk.payment.paymentservice.domain.consultation.Consultation;
import com.devtalk.payment.paymentservice.domain.payment.Payment;
import com.devtalk.payment.paymentservice.domain.payment.PaymentStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    private final ConsultationRepo consultationRepo;
    private final PaymentRepo paymentRepo;

    @KafkaListener(topics = "approved-consultation-topic")
    public void receiveConsultationInfo(String kafkaMessage) {
        log.info("Kafka Message: " + kafkaMessage);
//        Map<Object, Object> map = new HashMap<>();

        ConsultationInput consultation = null;

        try{
            // 카프카로 받은 메시지를 JSON 형태로 변환하기 위한 과정
            consultation = deserializeMapper().readValue(kafkaMessage, ConsultationInput.class);
//            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        dataSynchronization(consultation);
    }

    // TODO: 결제 취소건 topic 받기
    public void receiveRefundInfo(String kafkaMessage) {

    }

    // TODO: 예약건DB에 맞게 매핑해서 받아와야함
    private void dataSynchronization(Consultation consultation) {
        consultationRepo.save(consultation);
        paymentRepo.save(Payment.builder()
                .consultation(consultation)
                .paymentUid(null)
                .merchantId(consultation.getMerchantId())
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
