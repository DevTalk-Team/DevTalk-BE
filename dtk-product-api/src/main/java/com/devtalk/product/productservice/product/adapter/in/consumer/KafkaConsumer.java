package com.devtalk.product.productservice.product.adapter.in.consumer;

import com.devtalk.product.productservice.global.config.CustomLocalDateTimeDeserializer;

import com.devtalk.product.productservice.product.application.port.in.dto.MemberReq;
import com.devtalk.product.productservice.product.application.port.in.dto.ProductReservedDetailsReq;
import com.devtalk.product.productservice.product.application.port.out.repository.MemberRepo;
import com.devtalk.product.productservice.product.application.port.out.repository.ProductQueryableRepo;
import com.devtalk.product.productservice.product.application.port.out.repository.ProductRepo;
import com.devtalk.product.productservice.product.application.port.out.repository.ReservedProductRepo;
import com.devtalk.product.productservice.product.domain.member.Consultant;
import com.devtalk.product.productservice.product.domain.member.Consulter;
import com.devtalk.product.productservice.product.domain.member.MemberType;
import com.devtalk.product.productservice.product.domain.product.Product;
import com.devtalk.product.productservice.product.domain.product.ProductReservedDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    private final ProductRepo productRepo;
    private final MemberRepo memberRepo;
    private final ReservedProductRepo reservedProductRepo;

    // *** Topic은 변경할 수 있습니다. "test" 토픽으로 메시지를 확인해보세요 ***//
    @KafkaListener(topics = "member-signup")
    public void receiveMemberInfo(String kafkaMessage) {
        log.info("Kafka Message: " + kafkaMessage); // 로그 확인

        //*** 메시지를 받을 형태를 선언해주세요 ***//
        MemberReq memberReq = null;
        try{
            // 카프카로 받은 메시지를 객체로 변환하기 위한 과정
            //*** 받은 String형 메시지를 역직렬화 합니다. 받을 타입을 두번째 매개변수에 추가해주세요 ***//
            memberReq = deserializeMapper().readValue(kafkaMessage, MemberReq.class);
        } catch (JsonProcessingException ex){
            ex.printStackTrace();
        }
        //*** 받은 메시지로 수행할 행동을 아래에 써주세요***//
        dataSynchronization(memberReq);
        //**********************************************//
    }

    // *** 수행할 행동 *** //
    private void dataSynchronization(MemberReq memberReq) {
        if (memberReq.getMemberType() == MemberType.CONSULTER){
            Consulter consulter = Consulter.builder()
                    .phoneNumber(memberReq.getPhoneNumber())
                    .memberType(memberReq.getMemberType())
                    .name(memberReq.getName())
                    .build();
            memberRepo.save(consulter);
        }
        if (memberReq.getMemberType() == MemberType.CONSULTANT){
            Consultant consultant = Consultant.builder()
                    .phoneNumber(memberReq.getPhoneNumber())
                    .memberType(memberReq.getMemberType())
                    .name(memberReq.getName())
                    .NF2F_Price(memberReq.getNF2F_Price())
                    .F2F_Price(memberReq.getF2F_Price())
                    .region(memberReq.getRegion())
                    .build();
            memberRepo.save(consultant);
        }
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

    // *** 수행할 행동 *** //
    private void dataSynchronization(ProductReservedDetailsReq productReservedDetailsReq) {
        Optional<Product> optionalProduct = productRepo.findByConsultantIdAndReservationAt(
                productReservedDetailsReq.getConsultantId(),
                productReservedDetailsReq.getReservationAt()
        );

        Product product = optionalProduct.orElseThrow(() -> new NoSuchElementException("No Product found with the given criteria"));

        ProductReservedDetails productReservedDetails = ProductReservedDetails.builder()
                .product(product)
                .consulterId(productReservedDetailsReq.getConsulterId())
                .price(productReservedDetailsReq.getPrice())
                .reservedProceedType(productReservedDetailsReq.getReservedProceedType())
                .region(productReservedDetailsReq.getRegion())
                .build();

        reservedProductRepo.save(productReservedDetails);
    }


    //**********************************************************//


}