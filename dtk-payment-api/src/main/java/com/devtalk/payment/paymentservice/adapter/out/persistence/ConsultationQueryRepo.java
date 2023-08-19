package com.devtalk.payment.paymentservice.adapter.out.persistence;

import com.devtalk.payment.paymentservice.application.port.out.repository.ConsultationQueryableRepo;
import com.devtalk.payment.paymentservice.domain.consultation.Consultation;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class ConsultationQueryRepo implements ConsultationQueryableRepo {

}
