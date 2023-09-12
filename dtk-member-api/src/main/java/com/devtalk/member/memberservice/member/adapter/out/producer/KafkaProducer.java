package com.devtalk.member.memberservice.member.adapter.out.producer;

import com.devtalk.member.memberservice.global.config.CustomLocalDateTimeSerializer;
import com.devtalk.member.memberservice.member.domain.consultation.ConsultantInfo;
import com.devtalk.member.memberservice.member.domain.member.Member;
import com.devtalk.member.memberservice.member.domain.region.MemberRegion;
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

    public void sendMember(Member member) {
        String jsoinInString = "";
        try {
            jsoinInString = serializeMapper().writeValueAsString(member);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        kafkaTemplate.send("member-signup", jsoinInString);
        log.info("카프카 - 회원가입 멤버 전송 성공 : " + member);
    }

    public void sendConsultantInfo(ConsultantInfo consultantInfo) {
        String jsoinInString = "";
        try {
            jsoinInString = serializeMapper().writeValueAsString(consultantInfo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        kafkaTemplate.send("consultant-info", jsoinInString);
        log.info("카프카 - 전문가 정보 전송 성공 : " + consultantInfo);
    }

    public void sendConsultantRegion(MemberRegion memberRegion) {
        String jsoinInString = "";
        try {
            jsoinInString = serializeMapper().writeValueAsString(memberRegion);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        kafkaTemplate.send("consultant-info", jsoinInString);
        log.info("카프카 - 전문가 지역 전송 성공 : " + memberRegion);
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
