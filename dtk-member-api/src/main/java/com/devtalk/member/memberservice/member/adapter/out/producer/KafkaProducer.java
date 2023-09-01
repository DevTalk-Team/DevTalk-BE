package com.devtalk.member.memberservice.member.adapter.out.producer;

import com.devtalk.member.memberservice.global.config.CustomLocalDateTimeSerializer;
import com.devtalk.member.memberservice.member.domain.member.Member;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    //*** 두번째 파라미터를 변경해주세요 ***//
    public void send(String topic, Member member) {
        String jsonInString = "";
        try{
            //*** payment에는 보내고 싶은 객체를 넣어주세요 ***//
            jsonInString = serializeMapper().writeValueAsString(member);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        kafkaTemplate.send(topic, jsonInString);
        //*** 로그 확인 ***//
        log.info("카프카 메시지 전송 성공 : " + member);
    }

    public ObjectMapper serializeMapper() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(LocalDateTime.class, new CustomLocalDateTimeSerializer());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(simpleModule);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper;
    }
}
