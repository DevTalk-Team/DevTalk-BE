package com.devtalk.member.memberservice.member.adapter.in.consumer;

import com.devtalk.member.memberservice.global.config.CustomLocalDateTimeDeserializer;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
//    private final ConsultationRepo consultationRepo;
//    private final PaymentRepo paymentRepo;
//
//    // *** Topic은 변경할 수 있습니다. "test" 토픽으로 메시지를 확인해보세요 ***//
//    @KafkaListener(topics = "test")
//    public void receiveConsultationInfo(String kafkaMessage) {
//        log.info("Kafka Message: " + kafkaMessage); // 로그 확인
//
//        //*** 메시지를 받을 형태를 선언해주세요 ***//
//        Consultation consultation = null;
//        try{
//            // 카프카로 받은 메시지를 객체로 변환하기 위한 과정
//            //*** 받은 String형 메시지를 역직렬화 합니다. 받을 타입을 두번째 매개변수에 추가해주세요 ***//
//            consultation = deserializeMapper().readValue(kafkaMessage, Consultation.class);
//        } catch (JsonProcessingException ex){
//            ex.printStackTrace();
//        }
//        //*** 받은 메시지로 수행할 행동을 아래에 써주세요***//
//        dataSynchronization(consultation);
//        //**********************************************//
//    }
//
//    // *** 수행할 행동 *** //
//    private void dataSynchronization(Consultation consultation) {
//        consultationRepo.save(consultation);
//        paymentRepo.save(Payment.builder()
//                .consultation(consultation)
//                .paymentUid(null)
//                .merchantId(consultation.getMerchantId())
//                .cost(consultation.getCost())
//                .paidAt(null)
//                .status(PaymentStatus.READY)
//                .build());
//    }
    //**********************************************************//
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