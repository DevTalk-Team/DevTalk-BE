package com.devtalk.consultation.consultationservice.consultation.application;

import com.devtalk.consultation.consultationservice.consultation.adapter.out.producer.PaymentKafkaProducer;
import com.devtalk.consultation.consultationservice.consultation.adapter.out.producer.ProductKafkaProducer;
import com.devtalk.consultation.consultationservice.consultation.application.port.in.AcceptConsultationUseCase;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.client.ProductServiceClient;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.ConsultationQueryableRepo;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.Consultation;
import com.devtalk.consultation.consultationservice.global.error.execption.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.devtalk.consultation.consultationservice.global.error.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AcceptConsultationService implements AcceptConsultationUseCase {

    private final ConsultationQueryableRepo consultationQueryableRepo;
    private final ProductKafkaProducer productKafkaProducer;
    private final PaymentKafkaProducer paymentKafkaProducer;
    @Override
    public void acceptConsultation(Long consultantId, Long consultationId) {
        Consultation findConsultation = consultationQueryableRepo.findByIdWithConsultantId(consultationId, consultantId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_CONSULTATION));

        findConsultation.accept();
        productKafkaProducer.sendConsultationInfoProduct("consultation-topic", findConsultation);
       // paymentKafkaProducer.sendConsultationInfoPayment("approved-consultation-topic", findConsultation);
    }
}
