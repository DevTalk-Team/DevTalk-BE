package com.devtalk.payment.paymentservice.application;

import com.devtalk.payment.global.error.ErrorCode;
import com.devtalk.payment.global.error.exception.NotFoundException;
import com.devtalk.payment.paymentservice.application.port.in.ConsultationUseCase;
import com.devtalk.payment.paymentservice.application.port.in.EmailUseCase;
import com.devtalk.payment.paymentservice.application.port.in.PaymentUseCase;
import com.devtalk.payment.paymentservice.application.port.out.repository.PaymentRepo;
import com.devtalk.payment.paymentservice.domain.consultation.Consultation;
import com.devtalk.payment.paymentservice.domain.payment.Payment;
import com.devtalk.payment.paymentservice.domain.payment.PaymentStatus;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.devtalk.payment.paymentservice.application.port.in.dto.PaymentReq.*;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PaymentService implements PaymentUseCase {
    private final PaymentRepo paymentRepo;
    private final ConsultationUseCase consultationUseCase;
    private final EmailUseCase emailUseCase;

    // TODO: config로 관리해야함
    IamportClient iamportClient = new IamportClient("7728611378883883", "Aa8S38F7UhKtohATUSV9ILy7BExATIjJg9naXorRMox6AqcGl7ycVkbCAC6WXMoxX0oymhfPhjuaAB6m");

    @Override
    public PaymentServiceReq requestPaymentForm(Long consultationId) {
        Consultation consultation = consultationUseCase.searchConsultationInfo(consultationId);

        return PaymentServiceReq.builder()
                .consultationId(consultation.getId())
                .consulter(consultation.getConsulter())
                .consulterEmail(consultation.getConsulterEmail())
                .consultant(consultation.getConsultant())
                .consultationType(consultation.getConsultationType())
                .cost(consultation.getCost())
                .consultationAt(consultation.getConsultationAt())
                .build();
    }

    @Override
    public void requestPayment(Long consultationId) {
        Consultation consultation = consultationUseCase.searchConsultationInfo(consultationId);

        // 임시 결제 정보를 저장
        paymentRepo.save(Payment.builder()
                        .consultation(consultation)
                        .paymentUid(null)
                        .cost(consultation.getCost())
                        .paidAt(null)
                        .status(PaymentStatus.READY)
                        .build());
    }

    // 결제 검증
    @Override
    public IamportResponse<com.siot.IamportRestClient.response.Payment> paymentByCallback(PaymentCallbackReq request) {
        try{
            // 결제 단건 조회 (포트원)
            IamportResponse<com.siot.IamportRestClient.response.Payment> iamportResponse = iamportClient.paymentByImpUid(request.getPaymentUid());
            // 예약 내역 조회
            Payment payment = searchPaymentInfo(request.getConsultationId());
            // 결제 완료가 아니면
            if (!iamportResponse.getResponse().getStatus().equals("paid")) {
                // 임시 결제 삭제
                paymentRepo.delete(payment);
                throw new RuntimeException("결제 미완료");
            }

            // DB에 저장된 결제 금액
            Integer cost = payment.getCost();
            // 실 결제 금액
            int iamportCost = iamportResponse.getResponse().getAmount().intValue();

            // 결제 금액 검증
            if (iamportCost != cost) {
                // 임시 결제 삭제
                paymentRepo.delete(payment);

                // 결제금액 위변조로 의심되는 결제금액을 취소
                iamportClient.cancelPaymentByImpUid(
                        new CancelData(iamportResponse.getResponse().getImpUid(), true, new BigDecimal(iamportCost)));

                throw new RuntimeException("결제금액 위변조 의심");
            }

            // 결제 상태 변경
            payment.changePaymentBySuccess(PaymentStatus.PAID, iamportResponse.getResponse().getImpUid(), LocalDateTime.now());

            return iamportResponse;

        } catch (IamportResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Payment searchPaymentInfo(Long consultationId) {
        return paymentRepo.findByConsultationId(consultationId)
                .orElseThrow(()-> new NotFoundException(ErrorCode.NOT_FOUND_CONSULTATION));
    }
}
