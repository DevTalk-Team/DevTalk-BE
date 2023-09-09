package com.devtalk.product.productservice.product.adapter.in.consumer;

import com.devtalk.product.productservice.global.config.CustomLocalDateTimeDeserializer;

import com.devtalk.product.productservice.product.application.port.in.dto.ProductReservedDetailsReq;
import com.devtalk.product.productservice.product.application.port.in.product.ReserveUseCase;
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
public class ConsultationKafkaConsumer {
    private final ReserveUseCase reserveUseCase;

    // *** Topic은 변경할 수 있습니다. "test" 토픽으로 메시지를 확인해보세요 ***//
    @KafkaListener(topics = "consultation-reserve")
    public void receiveConsultationInfo(String kafkaMessage) {
        log.info("Kafka Message: " + kafkaMessage); // 로그 확인

        //*** 메시지를 받을 형태를 선언해주세요 ***//
        ProductReservedDetailsReq productReservedDetailsReq = null;
        try{
            // 카프카로 받은 메시지를 객체로 변환하기 위한 과정
            //*** 받은 String형 메시지를 역직렬화 합니다. 받을 타입을 두번째 매개변수에 추가해주세요 ***//
            productReservedDetailsReq = deserializeMapper().readValue(kafkaMessage, ProductReservedDetailsReq.class);
        } catch (JsonProcessingException ex){
            ex.printStackTrace();
        }
        //*** 받은 메시지로 수행할 행동을 아래에 써주세요***//
        dataSynchronization(productReservedDetailsReq);
        //**********************************************//
    }
    //**********************************************************//
    // *** 수행할 행동 *** //
    private void dataSynchronization(ProductReservedDetailsReq productReservedDetailsReq) {
        reserveUseCase.reserveProduct(productReservedDetailsReq);
    }

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