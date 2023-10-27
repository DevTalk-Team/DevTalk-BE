package com.devtalk.consultation.consultationservice.consultation.application;

import com.devtalk.consultation.consultationservice.consultation.adapter.out.producer.PaymentKafkaProducer;
import com.devtalk.consultation.consultationservice.consultation.adapter.out.producer.ProductKafkaProducer;
import com.devtalk.consultation.consultationservice.consultation.application.port.in.CancelConsultationUseCase;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.client.PaymentServiceClient;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.client.ProductServiceClient;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.client.dto.MemberReq;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.ConsultationCancellationRepo;
import com.devtalk.consultation.consultationservice.consultation.application.port.out.repository.ConsultationQueryableRepo;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.Consultation;
import com.devtalk.consultation.consultationservice.consultation.domain.consultation.ProcessStatus;
import com.devtalk.consultation.consultationservice.global.error.execption.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.devtalk.consultation.consultationservice.consultation.application.port.in.dto.ConsultationReq.*;
import static com.devtalk.consultation.consultationservice.global.error.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CancelConsultationService implements CancelConsultationUseCase {

    private final ConsultationQueryableRepo consultationQueryableRepo;
    private final ProductServiceClient productServiceClient;
    private final PaymentServiceClient paymentServiceClient;
    private final ConsultationCancellationRepo consultationCancellationRepo;
    private final PaymentKafkaProducer paymentKafkaProducer;
    private final ProductKafkaProducer productKafkaProducer;

    // TODO: 결제가 이미 된 매칭이라면
    // 1. 결제 서비스에 결제 취소요청
    // 2. 상품 서비스에 상품 취소요청

    @Override
    @Transactional
    public void cancelByConsulter(CancellationOfConsulterReq cancellationReq) {
        Consultation consultation = consultationQueryableRepo.findByIdWithConsulterId(cancellationReq.getConsultationId(), cancellationReq.getConsulterId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_CONSULTATION));

        ProcessStatus originProcessStatus = consultation.getStatus();

        consultation.cancelByConsulter(cancellationReq.getReason());
        consultationCancellationRepo.save(consultation);

        productKafkaProducer.sendConsultationInfoProduct("consultation-topic", consultation);

        if (originProcessStatus.equals(ProcessStatus.PAID)) {
            paymentServiceClient.refund(consultation.getId());
        }
    }

    @Override
    @Transactional
    public void cancelByConsultant(CancellationOfConsultantReq cancellationReq) {
        Consultation consultation = consultationQueryableRepo.findByIdWithConsultantId(cancellationReq.getConsultationId(), cancellationReq.getConsultantId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_CONSULTATION));

        ProcessStatus originProcessStatus = consultation.getStatus();

        consultation.cancelByConsultant(cancellationReq.getReason());
        consultationCancellationRepo.save(consultation);

        productKafkaProducer.sendConsultationInfoProduct("consultation-topic", consultation);

        if (originProcessStatus.equals(ProcessStatus.PAID)) {
            paymentServiceClient.refund(consultation.getId());
            //paymentKafkaProducer.sendConsultationInfoPayment("consultation-topic", consultation);
        }
    }
}